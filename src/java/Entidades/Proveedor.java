/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author thefi
 */
@Entity
@Table(name = "proveedores")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Proveedor.findAll", query = "SELECT p FROM Proveedor p")
    , @NamedQuery(name = "Proveedor.findByCodProveedor", query = "SELECT p FROM Proveedor p WHERE p.codProveedor = :codProveedor")
    , @NamedQuery(name = "Proveedor.findByNomProveedor", query = "SELECT p FROM Proveedor p WHERE p.nomProveedor = :nomProveedor")})
public class Proveedor implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "codProveedor")
    private Integer codProveedor;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "nomProveedor")
    private String nomProveedor;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codProveedor")
    private List<Pedidoproveedor> pedidoproveedorList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "proveedor")
    private List<Proveedorcomponente> proveedorcomponenteList;

    public Proveedor() {
    }

    public Proveedor(Integer codProveedor) {
        this.codProveedor = codProveedor;
    }

    public Proveedor(Integer codProveedor, String nomProveedor) {
        this.codProveedor = codProveedor;
        this.nomProveedor = nomProveedor;
    }

    public Integer getCodProveedor() {
        return codProveedor;
    }

    public void setCodProveedor(Integer codProveedor) {
        this.codProveedor = codProveedor;
    }

    public String getNomProveedor() {
        return nomProveedor;
    }

    public void setNomProveedor(String nomProveedor) {
        this.nomProveedor = nomProveedor;
    }

    @XmlTransient
    public List<Pedidoproveedor> getPedidoproveedorList() {
        return pedidoproveedorList;
    }

    public void setPedidoproveedorList(List<Pedidoproveedor> pedidoproveedorList) {
        this.pedidoproveedorList = pedidoproveedorList;
    }

    @XmlTransient
    public List<Proveedorcomponente> getProveedorcomponenteList() {
        return proveedorcomponenteList;
    }

    public void setProveedorcomponenteList(List<Proveedorcomponente> proveedorcomponenteList) {
        this.proveedorcomponenteList = proveedorcomponenteList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codProveedor != null ? codProveedor.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Proveedor)) {
            return false;
        }
        Proveedor other = (Proveedor) object;
        if ((this.codProveedor == null && other.codProveedor != null) || (this.codProveedor != null && !this.codProveedor.equals(other.codProveedor))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Proveedor[ codProveedor=" + codProveedor + " ]";
    }
    
}
