
package com.microsoft.schemas.sharepoint.soap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
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
 *         &lt;element name="strlistID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="strlistItemID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="strFieldName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "strlistID",
    "strlistItemID",
    "strFieldName"
})
@XmlRootElement(name = "GetVersionCollection")
public class GetVersionCollection {

    protected String strlistID;
    protected String strlistItemID;
    protected String strFieldName;

    /**
     * Obtiene el valor de la propiedad strlistID.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStrlistID() {
        return strlistID;
    }

    /**
     * Define el valor de la propiedad strlistID.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStrlistID(String value) {
        this.strlistID = value;
    }

    /**
     * Obtiene el valor de la propiedad strlistItemID.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStrlistItemID() {
        return strlistItemID;
    }

    /**
     * Define el valor de la propiedad strlistItemID.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStrlistItemID(String value) {
        this.strlistItemID = value;
    }

    /**
     * Obtiene el valor de la propiedad strFieldName.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStrFieldName() {
        return strFieldName;
    }

    /**
     * Define el valor de la propiedad strFieldName.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStrFieldName(String value) {
        this.strFieldName = value;
    }

}
