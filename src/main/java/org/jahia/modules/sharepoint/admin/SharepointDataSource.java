package org.jahia.modules.sharepoint.admin;

import com.google.common.collect.Sets;
import net.sf.ehcache.Ehcache;
import org.jahia.modules.external.ExternalData;
import org.jahia.modules.external.ExternalDataSource;
import org.jahia.modules.external.ExternalQuery;
import org.jahia.modules.external.query.QueryHelper;
import org.jahia.services.cache.ehcache.EhCacheProvider;
import org.jahia.services.content.nodetypes.NodeTypeRegistry;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.jcr.ItemNotFoundException;
import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;
import java.util.*;
import org.jahia.modules.sharepoint.SharepointCredentials;
import org.jahia.modules.sharepoint.SharepointList;
import org.jahia.modules.sharepoint.to.AssetTO;
import org.jahia.modules.sharepoint.util.StringUtils;

public class SharepointDataSource implements ExternalDataSource,  ExternalDataSource.Writable, ExternalDataSource.CanCheckAvailability, ExternalDataSource.Initializable,ExternalDataSource.Searchable {

    // Logger
    private static final Logger logger = LoggerFactory.getLogger(SharepointDataSource.class);

    
    //Sharepoint
    private List<AssetTO> spAssets;
    private SharepointList list;

    // Cache
    private EhCacheProvider ehCacheProvider;
    private Ehcache ecache;
    private static final String CACHE_AUTHOR            = "sharepoint-cache";
    private static final String CACHE_SHAREPOINT_ASSETS  = "cacheSharepointActivities";

    // Node types
    private static final String JNT_SHAREPOINT_ASSET  = "jnt:shAsset";
    private static final String JNT_CONTENT_FOLDER    = "jnt:contentFolder";

    // Sharepoint keys account
    private String user;
    private String password;
    private String port;
    private String protocol;
    private String host;
    private String libraryName;
    private String wsdlUrl;

    // Properties : sharepoint
    private static final String ID              = "id";
    private static final String AUTHOR          = "author";
    private static final String FILE_TYPE       = "fileType";
    private static final String FILE_NAME       = "fileName";
    private static final String TITLE           = "title";
    private static final String COMMENTS        = "comments";
    private static final String FILE_URL        = "fileUrl";


    // Properties : JCR
    private static final String ROOT            = "root";

    // Constants
    private static final String ASSET           = "asset";

    // CONSTRUCTOR
    public SharepointDataSource() {}

    // GETTERS AND SETTERS
    public void setUser(String user) {
        this.user = user;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPort(String port){
        this.port = port;
    }

    public void setProtocol(String protocol){
        this.protocol= protocol;
    }

    public void setHost(String host){
        this.host= host;
    }

    public void setLibraryName(String libraryName){
        this.libraryName= libraryName;
    }

    public void setWsdlUrl(String wsdlUrl) {this.wsdlUrl = wsdlUrl;}
    public void setCacheProvider(EhCacheProvider ehCacheProvider) { this.ehCacheProvider = ehCacheProvider; }


    // METHODS
    public void start() {
        setCache();
        setSharepointSession();
    }

    // UTILS
    private void setCache(){
        try {
            if (!ehCacheProvider.getCacheManager().cacheExists(CACHE_AUTHOR)) {
                ehCacheProvider.getCacheManager().addCache(CACHE_AUTHOR);
            }
            ecache = ehCacheProvider.getCacheManager().getCache(CACHE_AUTHOR);
        } catch (Exception e) {
            logger.error("Error with the cache : " + e.getMessage());
        }
    }

    private void cleanCache(){
        if (ehCacheProvider != null && !ehCacheProvider.getCacheManager().cacheExists(CACHE_AUTHOR)) {
            ehCacheProvider.getCacheManager().removeCache(CACHE_AUTHOR);
        }
        ecache = null;
    }

    private void setSharepointSession() {
        if (logger.isDebugEnabled())
        logger.info("setSharepointSession: getting the sharepoint session.");
       if (spAssets == null) {
           try {
               String user = this.user;
               String password = this.password;
               String port = this.port;
               String host = this.host;
               String protocol = this.protocol;
               String libraryName = this.libraryName;
               String wsdlUrl = this.wsdlUrl;
               SharepointCredentials credentials =
                       new SharepointCredentials(
                               user,
                               password,
                               host,
                               protocol,
                               wsdlUrl
                       );
               list = new SharepointList(credentials);
               spAssets = list.getAssetsByList(libraryName);
           } catch (Error err) {
               logger.error("setSharepointSession: could not get the sharepoint session.", err);
           }
       } else {
           logger.info("setSharePointSession: session already started.");
       }
    }
    /**
     * Execute a GET host/assets on the sharepoint API
     */
    private JSONArray querySharepoint() throws RepositoryException {
        JSONArray jsonArray = new JSONArray();
        spAssets = list.getAssetsByList(libraryName);
            try {
                for (AssetTO assetTO : spAssets) {
                    jsonArray.put(new JSONObject(assetTO));
                }
            } catch (Exception tex) {
                logger.error("querySharepoint: the Sharepoint Query return a error hosting by tag: ");
            }
        return jsonArray;
    }
    /**
     * Retieve all the sharepoint assets.
     * If deleteCache
     *      retrieve the assets on sharepoint (exemple : after the upload of a new asset)
     * Else
     *      retrieve the assets on cache
     */
    private JSONArray  getCacheSharepointActivities(boolean deleteCache) throws RepositoryException {
        JSONArray assets;
        if (ecache.get(CACHE_SHAREPOINT_ASSETS) != null && !deleteCache) {
            if (logger.isDebugEnabled())
            logger.info("Get the assets");
            assets = (JSONArray) ecache.get(CACHE_SHAREPOINT_ASSETS).getObjectValue();
        } else {
            if (logger.isDebugEnabled())
            logger.info("Refresh the assets");
            assets = querySharepoint();
        }
        return assets;
    }

    // IMPLEMENTS : ExternalDataSource
    @Override
    public List<String> getChildren(String path) throws RepositoryException {
        List<String> r = new ArrayList<>();
        if (path.equals("/")) {
            try {
                JSONArray assets = getCacheSharepointActivities(false);
                for (int i = 1; i <= assets.length(); i++) {
                    JSONObject asset = (JSONObject) assets.get(i - 1);
                    r.add(i + "-" + ASSET + "-" + asset.get(ID));
                }
                // example : 08-asset-401034489
            } catch (Exception e) {
                throw new RepositoryException(" getChildren Error " +  e);
            }
        }
        return r;
    }

    @Override
    public ExternalData getItemByIdentifier(String identifier) throws ItemNotFoundException {
        if (identifier.equals(ROOT)) {
            return new ExternalData(identifier, "/", JNT_CONTENT_FOLDER, new HashMap<String, String[]>());
        }
        Map<String, String[]> properties = new HashMap<>();
        String[] idAsset = identifier.split("-");
       if (idAsset.length == 3) {
            try {
                JSONArray assets = getCacheSharepointActivities(false);
                // Find the asset by its identifier
                int numAsset = Integer.parseInt(idAsset[0]) - 1;
                JSONObject asset = (JSONObject) assets.get(numAsset);
                // Add some properties
                properties.put(ID,                  new String[]{ asset.getString(ID)});
                properties.put(FILE_TYPE,           new String[]{ asset.getString(FILE_TYPE) });
                properties.put(TITLE,               new String[]{ asset.getString(TITLE) });
                properties.put(COMMENTS,            new String[]{ asset.getString(COMMENTS)});
                properties.put(AUTHOR,              new String[]{ asset.getString(AUTHOR)});
                properties.put(FILE_NAME,           new String[]{ asset.getString(FILE_NAME)});
                properties.put(FILE_URL,            new String[]{ asset.getString(FILE_URL)});
                ExternalData data = new ExternalData(identifier, "/" + identifier,JNT_SHAREPOINT_ASSET,properties);
                return data;
            } catch (Exception e) {
                throw new ItemNotFoundException(identifier);
            }
        } else {
            // Node not again created
            throw new ItemNotFoundException(identifier);
        }
    }

    @Override
    public ExternalData getItemByPath(String path) throws PathNotFoundException {
        String[] splitPath = path.split("/");
        try {
            if (splitPath.length <= 1) {
                return getItemByIdentifier(ROOT);
            } else {
                return getItemByIdentifier(splitPath[1]);
            }
        } catch (ItemNotFoundException e) {
            throw new PathNotFoundException(e);
        }
    }

    @Override
    public Set<String> getSupportedNodeTypes() {
        return Sets.newHashSet(JNT_CONTENT_FOLDER, JNT_SHAREPOINT_ASSET);
    }

    @Override
    public boolean isSupportsHierarchicalIdentifiers() {
        return false;
    }

    @Override
    public boolean isSupportsUuid() {
        return false;
    }

    @Override
    public boolean itemExists(String path) {
        return false;
    }

    // Implements : ExternalDataSource.Searchable

    @Override
    public List<String> search(ExternalQuery query) throws RepositoryException {
        List<String> paths = new ArrayList<>();
        String nodeType = QueryHelper.getNodeType(query.getSource());
        if (NodeTypeRegistry.getInstance().getNodeType(JNT_SHAREPOINT_ASSET).isNodeType(nodeType)) {
            try {
                JSONArray assets = getCacheSharepointActivities(true);

                for (int i = 1; i <= assets.length(); i++) {
                    JSONObject asset = (JSONObject) assets.get(i - 1);
                    String path = "/" + i + "-" + ASSET + "-" + asset.get(ID);
                    paths.add(path);
                }
                // example of a path : /08-asset-401034489
            } catch (JSONException e) {
                throw new RepositoryException(e);
            }
        }
        return paths;
    }

     @Override
    public void stop() {
        try {
            cleanCache();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public boolean isAvailable() {
        try{
            return true;
        }catch(Exception e){
            return false;
        }
    }


    // Implements : ExternalDataSource.Writable

    @Override
    public void move(String oldPath, String newPath) throws RepositoryException {
        if (logger.isDebugEnabled())
            logger.info("Move : oldPath=" + oldPath + " newPath=" + newPath);
    }
    @Override
    public void order(String path, List<String> children) throws RepositoryException {
        if (logger.isDebugEnabled())
            logger.info("Order : path=" + path);
    }

    @Override
    public void removeItemByPath(String path) throws RepositoryException {
        if (logger.isDebugEnabled())
            logger.info("Remove item by path : path=" + path);
    }

    @Override
    public void saveItem(ExternalData data) throws RepositoryException {
        AssetTO testUpdateTO = new AssetTO(data);
        try {
            list.updateListItem(this.libraryName, testUpdateTO);
        }catch(Exception e){
            logger.info("error: " + e.getMessage());
        }
        // After the upload, recuperation of the activies on Strava
        getCacheSharepointActivities(true);
    }
}
