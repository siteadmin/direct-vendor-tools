package org.sitenv.directvendortools.web.util;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import org.sitenv.directvendortools.web.util.ApplicationConstants;

@XStreamAlias(ApplicationConstants.RESULT_SET)
public class ResultSetTO
{

	@XStreamAsAttribute
	private int total;

	@XStreamAsAttribute
	private int totalRecordsUpdated;

	@XStreamImplicit
	private List<Object> results;
	
	private int page = 1;
	/**
	 * Default Constructor.
	 */
	public ResultSetTO()
	{

		results = new ArrayList<Object>();

	}

	/**
	 * @return the totalResultsReturned
	 */
	public int getTotalResultsReturned()
	{
		return total;
	}

	/**
	 * @param totalResultsReturned
	 *                the totalResultsReturned to set
	 */
	public void setTotalResultsReturned(final int totalResultsReturned)
	{
		this.total = totalResultsReturned;
	}

	public List<Object> getResults()
	{
		return results;
	}

	public void setResults(final List<Object> results)
	{
		this.results = results;
	}

	/**
	 * @return the totalRecordsUpdated
	 */
	public int getTotalRecordsUpdated()
	{
		return totalRecordsUpdated;
	}

	/**
	 * @param totalRecordsUpdated
	 *            the totalRecordsUpdated to set
	 */
	public void setTotalRecordsUpdated(final int totalRecordsUpdated)
	{
		this.totalRecordsUpdated = totalRecordsUpdated;
	}
	
	/**
	 * @return the page
	 */
	public int getPage() {
		return page;
	}

	/**
	 * @param page the page to set
	 */
	public void setPage(int page) {
		this.page = page;
	}

	public void initializeCounts()
	{
		int size = 0;
		if (getResults() != null)
		{
			size = getResults().size();
		}
		setTotalResultsReturned(size);
	}

	public String toString()
	{
		final StringBuffer stringBuffer = new StringBuffer("Results: ");
		stringBuffer.append(getResults());
		return stringBuffer.toString();
	}
}
