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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "componentes")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Componente.findAll", query = "SELECT c FROM Componente c")
    , @NamedQuery(name = "Componente.findByCodComponente", query = "SELECT c FROM Componente c WHERE c.codComponente = :codComponente")
    , @NamedQuery(name = "Componente.findByMarca", query = "SELECT c FROM Componente c WHERE c.codMarca = :laMarca")        
    , @NamedQuery(name = "Componente.findByDescripcion", query = "SELECT c FROM Componente c WHERE c.descripcion = :descripcion")})
public class Componente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "codComponente")
    private Integer codComponente;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "descripcion")
    private String descripcion;
    @JoinColumn(name = "codMarca", referencedColumnName = "codMarca")
    @ManyToOne(optional = false)
    private Marca codMarca;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codComponente")
    private List<Lineapproveedor> lineapproveedorList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "componente")
    private List<Proveedorcomponente> proveedorcomponenteList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "componente")
    private List<Productocomponente> productocomponenteList;

    public Componente() {
    }

    public Componente(Integer codComponente) {
        this.codComponente = codComponente;
    }

    public Componente(Integer codComponente, String descripcion) {
        this.codComponente = codComponente;
        this.descripcion = descripcion;
    }

    public Integer getCodComponente() {
        return codComponente;
    }

    public void setCodComponente(Integer codComponente) {
        this.codComponente = codComponente;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Marca getCodMarca() {
        return codMarca;
    }

    public void setCodMarca(Marca codMarca) {
        this.codMarca = codMarca;
    }

    @XmlTransient
    public List<Lineapproveedor> getLineapproveedorList() {
        return lineapproveedorList;
    }

    public void setLineapproveedorList(List<Lineapproveedor> lineapproveedorList) {
        this.lineapproveedorList = lineapproveedorList;
    }

    @XmlTransient
    public List<Proveedorcomponente> getProveedorcomponenteList() {
        return proveedorcomponenteList;
    }

    public void setProveedorcomponenteList(List<Proveedorcomponente> proveedorcomponenteList) {
        this.proveedorcomponenteList = proveedorcomponenteList;
    }

    @XmlTransient
    public List<Productocomponente> getProductocomponenteList() {
        return productocomponenteList;
    }

    public void setProductocomponenteList(List<Productocomponente> productocomponenteList) {
        this.productocomponenteList = productocomponenteList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codComponente != null ? codComponente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Componente)) {
            return false;
        }
        Componente other = (Componente) object;
        if ((this.codComponente == null && other.codComponente != null) || (this.codComponente != null && !this.codComponente.equals(other.codComponente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Componente[ codComponente=" + codComponente + " ]";
    }
    
}
