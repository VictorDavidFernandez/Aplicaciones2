/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author thefi
 */
@Entity
@Table(name = "pedidosclientes")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pedidocliente.findAll", query = "SELECT p FROM Pedidocliente p")
    , @NamedQuery(name = "Pedidocliente.findByCodPedidoCliente", query = "SELECT p FROM Pedidocliente p WHERE p.codPedidoCliente = :codPedidoCliente")
    , @NamedQuery(name = "Pedidocliente.findByCodCliente", query = "SELECT p FROM Pedidocliente p WHERE p.codCliente = :elCliente")
    , @NamedQuery(name = "Pedidocliente.findByFecha", query = "SELECT p FROM Pedidocliente p WHERE p.fecha = :fecha")
    , @NamedQuery(name = "Pedidocliente.findByFechas", query = "SELECT p FROM Pedidocliente p WHERE p.fecha BETWEEN :fechaInicial AND :fechaFinal")})
public class Pedidocliente implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "codPedidoCliente")
    private Integer codPedidoCliente;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codPedidoCliente")
    private List<Lineapcliente> lineapclienteList;
    @JoinColumn(name = "codCliente", referencedColumnName = "codCliente")
    @ManyToOne(optional = false)
    private Cliente codCliente;

    public Pedidocliente() {
    }

    public Pedidocliente(Integer codPedidoCliente) {
        this.codPedidoCliente = codPedidoCliente;
    }

    public Pedidocliente(Integer codPedidoCliente, Date fecha) {
        this.codPedidoCliente = codPedidoCliente;
        this.fecha = fecha;
    }

    public Integer getCodPedidoCliente() {
        return codPedidoCliente;
    }

    public void setCodPedidoCliente(Integer codPedidoCliente) {
        this.codPedidoCliente = codPedidoCliente;
    }


    @XmlTransient
    public List<Lineapcliente> getLineapclienteList() {
        return lineapclienteList;
    }

    public void setLineapclienteList(List<Lineapcliente> lineapclienteList) {
        this.lineapclienteList = lineapclienteList;
    }

    public Cliente getCodCliente() {
        return codCliente;
    }

    public void setCodCliente(Cliente codCliente) {
        this.codCliente = codCliente;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codPedidoCliente != null ? codPedidoCliente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pedidocliente)) {
            return false;
        }
        Pedidocliente other = (Pedidocliente) object;
        if ((this.codPedidoCliente == null && other.codPedidoCliente != null) || (this.codPedidoCliente != null && !this.codPedidoCliente.equals(other.codPedidoCliente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Pedidocliente[ codPedidoCliente=" + codPedidoCliente + " ]";
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    
}
