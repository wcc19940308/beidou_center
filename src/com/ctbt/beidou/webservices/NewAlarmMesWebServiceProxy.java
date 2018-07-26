package com.ctbt.beidou.webservices;

public class NewAlarmMesWebServiceProxy implements com.ctbt.beidou.webservices.NewAlarmMesWebService {
  private String _endpoint = null;
  private com.ctbt.beidou.webservices.NewAlarmMesWebService newAlarmMesWebService = null;
  
  public NewAlarmMesWebServiceProxy() {
    _initNewAlarmMesWebServiceProxy();
  }
  
  public NewAlarmMesWebServiceProxy(String endpoint) {
    _endpoint = endpoint;
    _initNewAlarmMesWebServiceProxy();
  }
  
  private void _initNewAlarmMesWebServiceProxy() {
    try {
      newAlarmMesWebService = (new com.ctbt.beidou.webservices.NewAlarmMesWebServiceServiceLocator()).getNewAlarmMesWebServicePort();
      if (newAlarmMesWebService != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)newAlarmMesWebService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)newAlarmMesWebService)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (newAlarmMesWebService != null)
      ((javax.xml.rpc.Stub)newAlarmMesWebService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.ctbt.beidou.webservices.NewAlarmMesWebService getNewAlarmMesWebService() {
    if (newAlarmMesWebService == null)
      _initNewAlarmMesWebServiceProxy();
    return newAlarmMesWebService;
  }
  
  public java.lang.String save(java.lang.String arg0) throws java.rmi.RemoteException{
    if (newAlarmMesWebService == null)
      _initNewAlarmMesWebServiceProxy();
    return newAlarmMesWebService.save(arg0);
  }
  
  
}