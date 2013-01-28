package gov.noaa.pmel.tmap.las.client.rpc;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gov.noaa.pmel.tmap.las.client.serializable.CategorySerializable;
import gov.noaa.pmel.tmap.las.client.serializable.ConfigSerializable;
import gov.noaa.pmel.tmap.las.client.serializable.DatasetSerializable;
import gov.noaa.pmel.tmap.las.client.serializable.ESGFDatasetSerializable;
import gov.noaa.pmel.tmap.las.client.serializable.FacetSerializable;
import gov.noaa.pmel.tmap.las.client.serializable.GridSerializable;
import gov.noaa.pmel.tmap.las.client.serializable.OperationSerializable;
import gov.noaa.pmel.tmap.las.client.serializable.OptionSerializable;
import gov.noaa.pmel.tmap.las.client.serializable.RegionSerializable;
import gov.noaa.pmel.tmap.las.client.serializable.TestSerializable;
import gov.noaa.pmel.tmap.las.client.serializable.VariableSerializable;

import com.google.gwt.user.client.rpc.RemoteService;
/**
 * GWT RPC Definitions for LAS.
 * @author rhs
 *
 */
public interface RPCService extends RemoteService {
	public CategorySerializable[] getCategories(String catid, String dsid) throws RPCException;
	public VariableSerializable getVariable(String dsid, String varid) throws RPCException;
	public GridSerializable getGrid(String dsID, String varID) throws RPCException;
	public OperationSerializable[] getOperations(String view, String dsID, String varID) throws RPCException;
	public OperationSerializable[] getOperations(String view, String[] xpath) throws RPCException;
	public OptionSerializable[] getOptions(String opid) throws RPCException;
	public CategorySerializable[] getTimeSeries() throws RPCException;
	public HashMap<String, String> getPropertyGroup(String name) throws RPCException;
	/**
	 * @param dsid
	 * @param varid
	 * @return an RegionSerializable array of named Regions such as the Indian Ocean
	 * @throws RPCException
	 */
	public RegionSerializable[] getRegions(String dsid, String varid) throws RPCException;
	public ConfigSerializable getConfig(String view, String dsid, String varid) throws RPCException;
	public Map<String, String> getIDMap(String data_url) throws RPCException;
	public TestSerializable[] getTestResults(String test_key) throws RPCException;
	public DatasetSerializable getFullDataset(String id) throws RPCException;
	/*
	 * Everything below is of the ESGF search interface.
	 */
	public List<FacetSerializable> getFacets() throws RPCException;
	public List<ESGFDatasetSerializable> getESGFDatasets(String query) throws RPCException;
	public String addESGFDataset(String id) throws RPCException;
}
