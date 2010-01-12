package gov.noaa.pmel.tmap.addxml;

import com.martiansoftware.jsap.*;
import com.martiansoftware.jsap.stringparsers.*;

/**
 * <p>Title: addXML</p>
 *
 * <p>Description: Reads local or OPeNDAP netCDF files and generates LAS XML
 * configuration information.</p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: NOAA/PMEL/TMAP</p>
 *
 * @author RHS
 * @version 1.0
 */
public class LAS_JSAP
    extends JSAP {
  public LAS_JSAP() {

    Switch verbose = new Switch("verbose")
        .setShortFlag('v')
        .setDefault("false")
        .setLongFlag("verbose");

    verbose.setHelp(
        "Switch to print out lots of helpful information about what addXML is doing.  Default is off.");

    Switch version = new Switch("version")
        .setShortFlag('V')
        .setDefault("false")
        .setLongFlag("version");

    version.setHelp(
        "Print version information.");
    
    Switch esg = new Switch("esg")
    .setShortFlag('E')
    .setDefault("false")
    .setLongFlag("ESG");

    esg.setHelp(
    "Look for ESG metadata in the data source.");
    
    FlaggedOption depth = new FlaggedOption("depth")
        .setStringParser(new StringStringParser())
        .setAllowMultipleDeclarations(false)
        .setRequired(false)
        .setShortFlag('D')
        .setLongFlag("depth");
        depth.setHelp("Depth to crawl to in the category tree");

    FlaggedOption ignore = new FlaggedOption("ignore")
        .setStringParser(new StringStringParser())
        .setAllowMultipleDeclarations(false)
        .setRequired(false)
        .setShortFlag('I')
        .setLongFlag("ignore");
        ignore.setHelp("Ignore urls containing this string");

    
    Switch listCategories = new Switch("listCategories")
        .setShortFlag('l')
        .setDefault("false")
        .setLongFlag("list_datasets");
        listCategories.setHelp("Only list category sizes");

    Switch log_bad_dsets = new Switch("log_bad_dsets")
        .setDefault("false")
        .setLongFlag("log_bad_dsets");
        log_bad_dsets.setHelp("Output log of bad datasets and categories");


    Switch categories_only = new Switch("categories_only")
        .setShortFlag('C')
        .setDefault("false")
        .setLongFlag("categories_only");
        categories_only.setHelp("Only create categories.");


    FlaggedOption exclude_file = new FlaggedOption("exclude_file")
        .setStringParser(new StringStringParser())
        .setAllowMultipleDeclarations(false)
        .setRequired(false)
        .setShortFlag('X')
        .setLongFlag("exclude_file");

     exclude_file.setHelp (
        "File that contains a list or URLs to exclude from the search.");

    FlaggedOption maxDatasets = new FlaggedOption("maxDatasets")
        .setStringParser(new StringStringParser())
        .setAllowMultipleDeclarations(false)
        .setRequired(false)
        .setLongFlag("maxDatasets");

     maxDatasets.setHelp (
        "Maximum number of datasets to allow in a category.");

    FlaggedOption maxCategories = new FlaggedOption("maxCategories")
        .setStringParser(new StringStringParser())
        .setAllowMultipleDeclarations(false)
        .setRequired(false)
        .setLongFlag("maxCategories");

     maxCategories.setHelp (
        "Maximum number of subcategories to allow in a category.");

    QualifiedSwitch dataset = (QualifiedSwitch)new QualifiedSwitch("dataset")
        .setStringParser(new StringStringParser())
        .setAllowMultipleDeclarations(false)
        .setRequired(false)
        .setShortFlag('d')
        .setLongFlag("dataset");

    dataset.setHelp("If present, all netCDF arguements will combined into one LAS dataset.  Optionally include the name to be given to the dataset");

    Switch category = new Switch("category")
        .setShortFlag('c')
        .setDefault("false")
        .setLongFlag("category");

    category.setHelp(
        "Create categories for the netCDF data sets being processed.");

    QualifiedSwitch arange = (QualifiedSwitch)new QualifiedSwitch("arange")
        .setStringParser(new StringStringParser())
        .setAllowMultipleDeclarations(false)
        .setRequired(false)
        .setList(true)
        .setListSeparator(',')
        .setShortFlag('a')
        .setLongFlag("arange");

    arange.setHelp("Force an arange element to be created for each listed (x,y,z,t) axes even if it is not regular.  E.g. -a:t,z");

    FlaggedOption in_xml = new FlaggedOption("in_xml")
        .setStringParser(new StringStringParser())
        .setAllowMultipleDeclarations(false)
        .setRequired(false)
        .setShortFlag('x')
        .setLongFlag("xml");

    in_xml.setHelp(
        "The file name of the las.xml file to which these data will be added.");

    FlaggedOption title_attribute = new FlaggedOption("title_attribute")
        .setStringParser(new StringStringParser())
        .setAllowMultipleDeclarations(false)
        .setRequired(false)
        .setShortFlag('g')
        .setLongFlag("title_attribute");

    title_attribute.setHelp(
        "The NC_GLOBAL attribute that contains the text that should be used as the LAS dataset name.");

    FlaggedOption in_thredds = new FlaggedOption("in_thredds")
        .setStringParser(new StringStringParser())
        .setAllowMultipleDeclarations(true)
        .setRequired(false)
        .setShortFlag('t')
        .setLongFlag("thredds");

    in_thredds.setHelp(
        "The file name or http:// URL of a THREDDS Catalog to be added.");

    FlaggedOption in_data = new FlaggedOption("in_netcdf")
        .setStringParser(new StringStringParser())
        .setAllowMultipleDeclarations(true)
        .setRequired(false)
        .setShortFlag('n')
        .setLongFlag("netcdf");

    in_data.setHelp(
        "The file name or OPeNDAP URL of the netCDF file to be added.");

    FlaggedOption format = new FlaggedOption("format")
    .setStringParser(new StringStringParser())
    .setAllowMultipleDeclarations(true)
    .setRequired(false)
    .setShortFlag('f')
    .setLongFlag("format");

   format.setHelp(
    "Set the format of how the time string is printed.  E.g. \"yyyy-MM\"");
   
   FlaggedOption units_format = new FlaggedOption("units_format")
   .setStringParser(new StringStringParser())
   .setAllowMultipleDeclarations(true)
   .setRequired(false)
   .setShortFlag('u')
   .setLongFlag("units_format");

  units_format.setHelp(
   "Set the format the date/time stamp in the origin part of the time units string.  E.g. for \"days since 1-1-1 00:00:00\" use \"y-M-d HH:mm:ss\".  You only need this if you calendar is not a Julian/Gregorian mix and your time format is not one of yyyy-MM-dd'T'HH:mm:ss.SSSZ or yyyy-MM-dd HH:mm:ss.");

	FlaggedOption auth_provider = new FlaggedOption("auth_provider")
	.setStringParser(new StringStringParser())
	.setAllowMultipleDeclarations(false)
	.setRequired(false)
	.setShortFlag('A')
	.setLongFlag("auth_provider");
	
	auth_provider.setHelp("Name of the class that provides authentication credentials.");

  	FlaggedOption username = new FlaggedOption("username")
  	.setStringParser(new StringStringParser())
  	.setAllowMultipleDeclarations(false)
  	.setRequired(false)
  	.setShortFlag('U')
  	.setLongFlag("username");
  	
  	username.setHelp("Username for network authentication.");
  	
  	FlaggedOption password = new FlaggedOption("password")
  	.setStringParser(new StringStringParser())
  	.setAllowMultipleDeclarations(false)
  	.setRequired(false)
  	.setShortFlag('P')
  	.setLongFlag("password");
  	
  	password.setHelp("Password for network authentication.");
  	
    UnflaggedOption basename = new UnflaggedOption("basename")
        .setStringParser(new StringStringParser())
        .setDefault("las_from_addXML")
        .setRequired(false)
        .setGreedy(false);
    basename.setHelp("Base name that will be used to construct output files names.  The edited --xml file goes in basename.xml and the data set information goes in basename_000.xml, basename_001.xml, ...");

    
    Switch irregular = new Switch("irregular")
    .setShortFlag('i')
    .setDefault("false")
    .setLongFlag("irregular");

    version.setHelp("Treat time axis as irregular axis and make give it an <arange> of whole hours.  Typically used for high-frequncey sesnor data.");
    
    FlaggedOption groupname = new FlaggedOption("groupname")
  	.setStringParser(new StringStringParser())
  	.setAllowMultipleDeclarations(false)
  	.setRequired(false)
  	.setShortFlag('G')
  	.setLongFlag("groupname");
  	
  	groupname.setHelp("Name of the LAS group to which this data collection belongs.");
  	
  	QualifiedSwitch grouptype = (QualifiedSwitch)new QualifiedSwitch("grouptype")
    .setStringParser(new StringStringParser())
    .setAllowMultipleDeclarations(false)
    .setRequired(false)
    .setList(true)
    .setListSeparator(',')
    .setShortFlag('T')
    .setLongFlag("grouptype");

    grouptype.setHelp("Set the group type.  The choices are: ensemble or time_series.  E.g. -T:time_series");
    
    try {
      this.registerParameter(in_thredds);
      this.registerParameter(in_data);
      this.registerParameter(category);
      this.registerParameter(dataset);
      this.registerParameter(in_xml);
      this.registerParameter(arange);
      this.registerParameter(title_attribute);
      this.registerParameter(verbose);
      this.registerParameter(version);
      this.registerParameter(basename);
      this.registerParameter(format);
      this.registerParameter(units_format);
      this.registerParameter(auth_provider);
      this.registerParameter(username);
      this.registerParameter(password);
      this.registerParameter(irregular);
      this.registerParameter(groupname);
      this.registerParameter(grouptype);
      this.registerParameter(esg);
      this.registerParameter(categories_only);
      this.registerParameter(exclude_file);
      this.registerParameter(listCategories);
      this.registerParameter(maxCategories);
      this.registerParameter(maxDatasets);
       this.registerParameter(depth);
	 this.registerParameter(log_bad_dsets);
	 this.registerParameter(ignore);
	}
    catch (JSAPException ex) {
      this.errorout();
    }

  }

  /**
   * errorout
   */
  public void errorout() {
    errorout(null);
  }

  /**
   * errorout
   */
  public void errorout(JSAPResult config) {

    if (config != null) {
      for (java.util.Iterator errs = config.getErrorMessageIterator();
           errs.hasNext(); ) {
        System.err.println("Error: " + errs.next());
      }
    }
    System.err.println();
    System.err.print("Usage: addXML.sh ");
    System.err.println(" " + this.getUsage());
    System.err.println();
    System.err.println(this.getHelp());
    System.exit(1);

  }
}
