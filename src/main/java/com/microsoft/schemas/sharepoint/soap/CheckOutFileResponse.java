
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
 *         &lt;element name="CheckOutFileResult" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
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
    "checkOutFileResult"
})
@XmlRootElement(name = "CheckOutFileResponse")
public class CheckOutFileResponse {

    @XmlElement(name = "CheckOutFileResult")
    protected boolean checkOutFileResult;

    /**
     * Obtiene el valor de la propiedad checkOutFileResult.
     * 
     */
    public boolean isCheckOutFileResult() {
        return checkOutFileResult;
    }

    /**
     * Define el valor de la propiedad checkOutFileResult.
     * 
     */
    public void setCheckOutFileResult(boolean value) {
        this.checkOutFileResult = value;
    }

}
