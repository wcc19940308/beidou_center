/**
 * NewAlarmMesWebServiceServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ctbt.beidou.webservices;

public class NewAlarmMesWebServiceServiceLocator extends org.apache.axis.client.Service implements com.ctbt.beidou.webservices.NewAlarmMesWebServiceService {

    public NewAlarmMesWebServiceServiceLocator() {
    }


    public NewAlarmMesWebServiceServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public NewAlarmMesWebServiceServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for NewAlarmMesWebServicePort
    private java.lang.String NewAlarmMesWebServicePort_address = "http://115.231.112.227:8088/CTBT/services/NewAlarmMes";

    public java.lang.String getNewAlarmMesWebServicePortAddress() {
        return NewAlarmMesWebServicePort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String NewAlarmMesWebServicePortWSDDServiceName = "NewAlarmMesWebServicePort";

    public java.lang.String getNewAlarmMesWebServicePortWSDDServiceName() {
        return NewAlarmMesWebServicePortWSDDServiceName;
    }

    public void setNewAlarmMesWebServicePortWSDDServiceName(java.lang.String name) {
        NewAlarmMesWebServicePortWSDDServiceName = name;
    }

    public com.ctbt.beidou.webservices.NewAlarmMesWebService getNewAlarmMesWebServicePort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(NewAlarmMesWebServicePort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getNewAlarmMesWebServicePort(endpoint);
    }

    public com.ctbt.beidou.webservices.NewAlarmMesWebService getNewAlarmMesWebServicePort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.ctbt.beidou.webservices.NewAlarmMesWebServiceServiceSoapBindingStub _stub = new com.ctbt.beidou.webservices.NewAlarmMesWebServiceServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getNewAlarmMesWebServicePortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setNewAlarmMesWebServicePortEndpointAddress(java.lang.String address) {
        NewAlarmMesWebServicePort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.ctbt.beidou.webservices.NewAlarmMesWebService.class.isAssignableFrom(serviceEndpointInterface)) {
                com.ctbt.beidou.webservices.NewAlarmMesWebServiceServiceSoapBindingStub _stub = new com.ctbt.beidou.webservices.NewAlarmMesWebServiceServiceSoapBindingStub(new java.net.URL(NewAlarmMesWebServicePort_address), this);
                _stub.setPortName(getNewAlarmMesWebServicePortWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("NewAlarmMesWebServicePort".equals(inputPortName)) {
            return getNewAlarmMesWebServicePort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://webservice.ctbt.com/", "NewAlarmMesWebServiceService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://webservice.ctbt.com/", "NewAlarmMesWebServicePort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("NewAlarmMesWebServicePort".equals(portName)) {
            setNewAlarmMesWebServicePortEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
