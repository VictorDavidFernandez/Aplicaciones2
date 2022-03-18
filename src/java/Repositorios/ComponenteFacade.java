/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Repositorios;

import Entidades.Componente;
import Entidades.Lineapcliente;
import Entidades.Marca;
import Entidades.Productocomponente;
import Entidades.Productopropio;
import Entidades.Proveedor;
import Entidades.Proveedorcomponente;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author thefi
 */
@Stateless
public class ComponenteFacade extends AbstractFacade<Componente> {

    @PersistenceContext(unitName = "Aplicaciones2PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ComponenteFacade() {
        super(Componente.class);
    }
    public List<Componente> listaComponentes(Marca marca){
        EntityManager em = getEntityManager();
        Query q;
        if(marca != null){
            q = em.createNamedQuery("Componente.findByMarca").setParameter("laMarca", marca);
            return q.getResultList();
        }
        return null;
    }

    public List<Proveedorcomponente> proveedoresPrecio(Proveedor proveedor){
        EntityManager em = getEntityManager();
        Query q;
        if(proveedor != null){
            q = em.createNamedQuery("Proveedorcomponente.findByCodProveedor").setParameter("codProveedor", proveedor.getCodProveedor());
            return q.getResultList();
        }
        return null;
    } 
    public List<Productocomponente> listaProductosComponente(Productopropio producto){
        EntityManager em = getEntityManager();
        Query q;
        if(producto != null){
            q = em.createNamedQuery("Productocomponente.findByCodProducto").setParameter("codProducto", producto.getCodProducto());
            return q.getResultList();
        }
        return null;
    }
    
    public List<Proveedorcomponente> componentePrecio(Componente componente){
        EntityManager em = getEntityManager();
        Query q;
        if(componente != null){
            q = em.createNamedQuery("Proveedorcomponente.findByCodComponente").setParameter("codComponente", componente.getCodComponente());
            return q.getResultList();
        }
        return null;
    } 
    public List<Lineapcliente> lineasPedidoCliente(Productopropio producto){
        EntityManager em = getEntityManager();
        Query q;
        if(producto != null){
            q = em.createNamedQuery("Lineapcliente.findByProducto").setParameter("elProducto", producto);
            return q.getResultList();
        }
        return null;
    }
}
