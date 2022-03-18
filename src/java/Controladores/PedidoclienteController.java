package Controladores;

import Entidades.Pedidocliente;
import Controladores.util.JsfUtil;
import Controladores.util.PaginationHelper;
import Entidades.Cliente;
import Entidades.Lineapcliente;
import Repositorios.PedidoclienteFacade;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;

@Named("pedidoclienteController")
@SessionScoped
public class PedidoclienteController implements Serializable {

    private Pedidocliente current;
    private DataModel items = null;
    @EJB
    private Repositorios.PedidoclienteFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private Cliente cliente;
    private List<Pedidocliente> pedidosCliente;
    private List<Lineapcliente> lineasPedido;
    private SelectItem[] desplegablePedidos;
    private Pedidocliente pedidoCliente;
    private Date fechaInicial;
    private Date fechaFinal;
    private List<Pedidocliente> listaPedidosClienteFechas;

    public List<Pedidocliente> getListaPedidosClienteFechas() {
        return listaPedidosClienteFechas;
    }

    public void setListaPedidosClienteFechas(List<Pedidocliente> listaPedidosClienteFechas) {
        this.listaPedidosClienteFechas = listaPedidosClienteFechas;
    }
    
    public Date getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public Date getFechaInicial() {
        return fechaInicial;
    }

    public void setFechaInicial(Date fechaInicial) {
        this.fechaInicial = fechaInicial;
    }

    public Pedidocliente getPedidoCliente() {
        return pedidoCliente;
    }

    public void setPedidoCliente(Pedidocliente pedidoCliente) {
        this.pedidoCliente = pedidoCliente;
    }

    public List<Pedidocliente> getPedidosCliente() {
        return pedidosCliente;
    }

    public void setPedidosCliente(List<Pedidocliente> pedidosCliente) {
        this.pedidosCliente = pedidosCliente;
    }

    public List<Lineapcliente> getLineasPedido() {
        return lineasPedido;
    }

    public void setLineasPedido(List<Lineapcliente> lineasPedido) {
        this.lineasPedido = lineasPedido;
    }

    public SelectItem[] getDesplegablePedidos() {
        return desplegablePedidos;
    }

    public void setDesplegablePedidos(SelectItem[] desplegablePedidos) {
        this.desplegablePedidos = desplegablePedidos;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public PedidoclienteController() {
    }

    public Pedidocliente getSelected() {
        if (current == null) {
            current = new Pedidocliente();
            selectedItemIndex = -1;
        }
        return current;
    }

    private PedidoclienteFacade getFacade() {
        return ejbFacade;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {

                @Override
                public int getItemsCount() {
                    return getFacade().count();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
                }
            };
        }
        return pagination;
    }

    public String prepareList() {
        recreateModel();
        return "List";
    }

    public String prepareView() {
        current = (Pedidocliente) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Pedidocliente();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("PedidoclienteCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Pedidocliente) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("PedidoclienteUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Pedidocliente) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "List";
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "View";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "List";
        }
    }

    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("PedidoclienteDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = getFacade().count();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getFacade().findRange(new int[]{selectedItemIndex, selectedItemIndex + 1}).get(0);
        }
    }

    public DataModel getItems() {
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        return items;
    }

    private void recreateModel() {
        items = null;
    }

    private void recreatePagination() {
        pagination = null;
    }

    public String next() {
        getPagination().nextPage();
        recreateModel();
        return "List";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "List";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }
    
    public SelectItem[] cargarDesplegablePedidos() {
        if(pedidosCliente != null)
            return desplegablePedidos = getSelectItems(pedidosCliente, true);
        return null;
    }

    public Pedidocliente getPedidocliente(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = Pedidocliente.class)
    public static class PedidoclienteControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            PedidoclienteController controller = (PedidoclienteController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "pedidoclienteController");
            return controller.getPedidocliente(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Pedidocliente) {
                Pedidocliente o = (Pedidocliente) object;
                return getStringKey(o.getCodPedidoCliente());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Pedidocliente.class.getName());
            }
        }

    }
    public void cargarPedidosCliente() {
        if(cliente!=null)
            pedidosCliente = ejbFacade.pedidosCliente(cliente);
    }
    
    public void cargarLineasPedido() {
        if(pedidoCliente!=null)
            lineasPedido = ejbFacade.pedidosCliente(pedidoCliente);
    }
    
    public static SelectItem[] getSelectItems(List<Pedidocliente> entities, boolean selectOne) {
        int size = selectOne ? entities.size() + 1 : entities.size();
        SelectItem[] items = new SelectItem[size];
        int i = 0;
        if (selectOne) {
            items[0] = new SelectItem("-1", "Selecciona un pedido");
            i++;
        }
        for (Pedidocliente x : entities) {
            items[i++] = new SelectItem(x, x.getCodPedidoCliente().toString()/*+ " " + x.getApellido2() + ", " + x.getNomAutor()*/);
        }
        return items;
    } 
    
    public void cargarPedidosClienteFechas() {
        if(fechaInicial !=null && fechaFinal !=null)
            listaPedidosClienteFechas = ejbFacade.pedidosClienteFechas(fechaInicial, fechaFinal);
    }
    
}
