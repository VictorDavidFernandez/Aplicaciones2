/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Repositorios;

import Entidades.Lineapproveedor;
import Entidades.Pedidocliente;
import Entidades.Pedidoproveedor;
import Entidades.Proveedor;
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
public class PedidoproveedorFacade extends AbstractFacade<Pedidoproveedor> {

    @PersistenceContext(unitName = "Aplicaciones2PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PedidoproveedorFacade() {
        super(Pedidoproveedor.class);
    }
    public List<Pedidoproveedor> pedidosProveedor(Proveedor proveedor){
        EntityManager em = getEntityManager();
        Query q;
        q = em.createNamedQuery("Pedidoproveedor.findByProveedor").setParameter("elProveedor", proveedor);
        return q.getResultList();
    }  
    
    public List<Lineapproveedor> pedidosProveedorLineas(Pedidoproveedor pedidoProveedor){
        EntityManager em = getEntityManager();
        Query q;
        q = em.createNamedQuery("Lineapproveedor.findByPedido").setParameter("elPedido", pedidoProveedor);
        return q.getResultList();
    }    
}
