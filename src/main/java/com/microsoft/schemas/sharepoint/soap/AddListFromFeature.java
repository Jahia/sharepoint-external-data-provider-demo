
package com.microsoft.schemas.sharepoint.soap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para anonymous complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="listName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="featureID" type="{http://microsoft.com/wsdl/types/}guid"/>
 *         &lt;element name="templateID" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "listName",
    "description",
    "featureID",
    "templateID"
})
@XmlRootElement(name = "AddListFromFeature")
public class AddListFromFeature {

    protected String listName;
    protected String description;
    @XmlElement(required = true)
    protected String featureID;
    protected int templateID;

    /**
     * Obtiene el valor de la propiedad listName.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getListName() {
        return listName;
    }

    /**
     * Define el valor de la propiedad listName.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setListName(String value) {
        this.listName = value;
    }

    /**
     * Obtiene el valor de la propiedad description.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Define el valor de la propiedad description.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Obtiene el valor de la propiedad featureID.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFeatureID() {
        return featureID;
    }

    /**
     * Define el valor de la propiedad featureID.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFeatureID(String value) {
        this.featureID = value;
    }

    /**
     * Obtiene el valor de la propiedad templateID.
     * 
     */
    public int getTemplateID() {
        return templateID;
    }

    /**
     * Define el valor de la propiedad templateID.
     * 
     */
    public void setTemplateID(int value) {
        this.templateID = value;
    }

}
