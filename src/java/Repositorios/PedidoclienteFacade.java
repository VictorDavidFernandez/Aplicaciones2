/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Repositorios;

import Entidades.Cliente;
import Entidades.Componente;
import Entidades.Lineapcliente;
import Entidades.Marca;
import Entidades.Pedidocliente;
import java.util.Date;
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
public class PedidoclienteFacade extends AbstractFacade<Pedidocliente> {

    @PersistenceContext(unitName = "Aplicaciones2PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PedidoclienteFacade() {
        super(Pedidocliente.class);
    }
    
    public List<Pedidocliente> pedidosCliente(Cliente cliente){
        EntityManager em = getEntityManager();
        Query q;
        q = em.createNamedQuery("Pedidocliente.findByCodCliente").setParameter("elCliente", cliente);
        return q.getResultList();
    }

    public List<Lineapcliente> pedidosCliente(Pedidocliente pedido){
        EntityManager em = getEntityManager();
        Query q;
        q = em.createNamedQuery("Lineapcliente.findByPedido").setParameter("elPedido", pedido);
        return q.getResultList();
    } 
    
    public List<Pedidocliente> pedidosClienteFechas(Date fechaInicial, Date fechaFinal){
        EntityManager em = getEntityManager();
        Query q;
        q = em.createNamedQuery("Pedidocliente.findByFechas").setParameter("fechaInicial", fechaInicial).setParameter("fechaFinal", fechaFinal);
        return q.getResultList();
    }    
}
