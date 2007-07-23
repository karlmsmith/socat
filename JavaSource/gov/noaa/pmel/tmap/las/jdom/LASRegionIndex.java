package gov.noaa.pmel.tmap.las.jdom;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import org.jdom.Element;

public class LASRegionIndex extends LASDocument {

    private static Logger log = LogManager.getLogger(LASRegionIndex.class.getName());
    private static final long serialVersionUID = 703675281298155235L;
    /**
     * 
     */
    
    public LASRegionIndex() {
        super();
    }
    
    public LASRegionIndex(File index) throws IOException {
        super();
        BufferedReader indexReader;

        FileReader f = new FileReader(index);
        indexReader = new BufferedReader(f);

        ArrayList<HashMap> sections = new ArrayList<HashMap>();

        String line = indexReader.readLine();
        HashMap<String, String> section = new HashMap<String, String>();
        HashMap<String, String> original = new HashMap<String, String>();
        while ( line != null ) {
            /* The Ferret output looks like this:
                 "data region" " "
                 "original_region"
                 "original_x_lo" "-180.0"
                 "original_x_hi" "180.0"
                 "original_y_lo" "-89.0"
                 "original_y_hi" "89.0"
                 "original_z_lo" " "
                 "original_z_hi" " "
                 "original_t_lo" "15-Jan"
                 "original_t_hi" "15-Jan"
                 "original_k_lo" " "
                 "original_k_hi" " "
                 "original_l_lo" "1"
                 "original_l_hi" "1"
                 "end original_region"
                 "section" "1"
                 "x_lo"    "-181"
                 "x_hi"    "19"
                 "i_lo"    "-100"
                 "i_hi"    "0"
                 "y_lo"    "-89"
                 "y_hi"    "89"
                 "j_lo"    "1"
                 "j_hi"    "90"
                 "z_lo"    " "
                 "z_hi"    " "
                 "k_lo"    " "
                 "k_hi"    " "
                 "t_lo"    "16-JAN"
                 "t_hi"    "16-JAN"
                 "l_lo"    "1"
                 "l_hi"    "1"
                 "end section"
                 "section" "2"
                 "x_lo"    "59"
                 "x_hi"    "179"
                 "i_lo"    "20"
                 "i_hi"    "80"
                 "y_lo"    "-89"
                 "y_hi"    "89"
                 "j_lo"    "1"
                 "j_hi"    "90"
                 "z_lo"    " "
                 "z_hi"    " "
                 "k_lo"    " "
                 "k_hi"    " "
                 "t_lo"    "16-JAN"
                 "t_hi"    "16-JAN"
                 "l_lo"    "1"
                 "l_hi"    "1"
                 "end section"
                 "end region" 
             */
            if ( line.contains("\"section\"") ) {
                if (section.size() == 0) {
                    sections.add(section);
                }
                else if ( section.size() > 0 ) {                  
                    section = new HashMap<String, String>();
                    sections.add(section);
                }
            } else if ( (line.contains("hi") || line.contains("lo")) && !line.contains("original") ) {
                // Split on the " and use the parts.  See JavaDoc on split for explanation...
                String[] parts = line.split("\"");
                section.put(parts[1], parts[3]);
            } else if ( (line.contains("hi") || line.contains("lo")) && line.contains("original") ) {
                String[] parts = line.split("\"");
                original.put(parts[1], parts[3]);
            }
            line = indexReader.readLine();
        }     
        Element region = new Element("region");

        this.setRootElement(region);
        
        Element originalE = new Element("original");
        for (Iterator oIt = original.keySet().iterator(); oIt.hasNext();) {
            String key = (String) oIt.next();
            String value = (String) original.get(key);
            Element keyE = new Element(key);
            keyE.setText(value);
            originalE.addContent(keyE);
        }
        
        region.addContent(originalE);

        log.debug("Found "+sections.size()+" sections.");

        int sectionIndex = 0;
        int isec = 0;
        for (Iterator secIt = sections.iterator(); secIt.hasNext();) {
            sectionIndex++;
            Element sectionE = new Element("section");
            sectionE.setAttribute("index", String.valueOf(sectionIndex));
            HashMap sectionHM = (HashMap) secIt.next();
            isec = sectionIndex + 1;
            log.debug("Adding section "+isec+".");
            for (Iterator keys = sectionHM.keySet().iterator(); keys.hasNext();) {
                String key = (String) keys.next();
                String value = (String) sectionHM.get(key);
                Element keyE = new Element(key);
                keyE.setText(value);
                sectionE.addContent(keyE);
            }
            region.addContent(sectionE);
        }
    }
    public String getOPeNDAPConstraint(int section_index) {
        String constraint = "";
        ArrayList sections = getSections();
        HashMap section = (HashMap) sections.get(section_index);
        if ( section.containsKey("t") ) {
            constraint = constraint+"["+((HashMap)section.get("t")).get("index_lo")+","+((HashMap)section.get("t")).get("index_hi")+"]";
        }
        if ( section.containsKey("z") ) {
            constraint = constraint+"["+((HashMap)section.get("z")).get("index_lo")+","+((HashMap)section.get("z")).get("index_hi")+"]";
        }
        if ( section.containsKey("y") ) {
            constraint = constraint+"["+((HashMap)section.get("y")).get("index_lo")+","+((HashMap)section.get("y")).get("index_hi")+"]";
        }
        if ( section.containsKey("x") ) {
            constraint = constraint+"["+((HashMap)section.get("x")).get("index_lo")+","+((HashMap)section.get("x")).get("index_hi")+"]";
        }
        return constraint;
    }
    public String getRangesCovered(int section_index) {
        String constraint = "% Region covered by this URL ";
        ArrayList sections = getSections();
        HashMap section = (HashMap) sections.get(section_index);
        if ( section.containsKey("t") ) {
            constraint = constraint+"t="+((HashMap)section.get("t")).get("lo")+":"+((HashMap)section.get("t")).get("hi")+" ";
        }
        if ( section.containsKey("z") ) {
            constraint = constraint+"z="+((HashMap)section.get("z")).get("lo")+":"+((HashMap)section.get("z")).get("hi")+" ";
        }
        if ( section.containsKey("y") ) {
            constraint = constraint+"y="+((HashMap)section.get("y")).get("lo")+":"+((HashMap)section.get("y")).get("hi")+" ";
        }
        if ( section.containsKey("x") ) {
            constraint = constraint+"x="+((HashMap)section.get("x")).get("lo")+":"+((HashMap)section.get("x")).get("hi")+" ";
        }
        return constraint;
    }
    public String getFerretRegion() {
        
        HashMap<String, HashMap<String,String>> original = getOriginalRegion();
        
        HashMap<String, String> extent = (HashMap<String, String>)original.get("x");
        
        String xlo = null;
        String xhi = null;
        String ylo = null;
        String yhi = null;
        String zlo = null;
        String zhi = null;
        String tlo = null;
        String thi = null;
        
        if ( extent != null ) {
            xlo = (String)extent.get("lo");
            xhi = (String)extent.get("hi");
        }
        
        extent = (HashMap<String, String>)original.get("y");
        
        if ( extent != null ) {
            ylo = (String)extent.get("lo");
            yhi = (String)extent.get("hi");
        }
        
        extent = (HashMap<String, String>)original.get("z");
        
        if ( extent != null ) {
            zlo = (String)extent.get("lo");
            zhi = (String)extent.get("hi");
        }

        extent = (HashMap<String, String>)original.get("t");
        
        if (extent != null ) {
            tlo = (String)extent.get("lo");
            thi = (String)extent.get("hi");
        }
        
        
        
        String region = "SET REGION";
        if ( xlo != null && !xlo.equals("") && !xlo.equals(" ") && !xlo.equals("  ")) {
            region = region+"/x=\""+xlo+"\":\""+xhi+"\"";
        }
        if ( ylo != null && !ylo.equals("") && !ylo.equals(" ") && !ylo.equals("  ")) {
            region = region+"/y=\""+ylo+"\":\""+yhi+"\"";
        }
        if ( zlo != null && !zlo.equals("") && !zlo.equals(" ") && !zlo.equals("  ")) {
            region = region+"/z=\""+zlo+"\":\""+zhi+"\"";
        }
        if ( tlo != null && !tlo.equals("") && !tlo.equals(" ") && !tlo.equals("  ")) {
            region = region+"/t=\""+tlo+"\":\""+thi+"\"";
        }
        
        return region;
        
    }
    
    public HashMap<String, HashMap<String, String>> getOriginalRegion() {
        HashMap<String, HashMap<String, String>> original = new HashMap<String, HashMap<String, String>>();
        Element originalE = this.getRootElement().getChild("original");
        HashMap<String, String> x = new HashMap<String, String>();
        HashMap<String, String> y = new HashMap<String, String>();
        HashMap<String, String> z = new HashMap<String, String>();
        HashMap<String, String> t = new HashMap<String, String>();
        List extents = originalE.getChildren();
        for (Iterator exIt = extents.iterator(); exIt.hasNext();) {
            Element extent = (Element) exIt.next();
            String name = extent.getName();
            String value = extent.getTextNormalize();
            if ( name.contains("x_lo") || name.contains("x_hi") ) {              
                if ( value != null && !value.equals("") && !value.equals(" ") && !value.equals("  ")) {
                    if ( name.equals("original_x_lo")) {
                        x.put("lo", value);
                    }
                    if ( name.equals("original_x_hi")) {
                        x.put("hi", value);
                    }
                }
                if ( x.size() > 0 ) {
                   original.put("x", x);
                }
            }
            if ( name.contains("y_lo") || name.contains("y_hi")) {                
                if ( value != null && !value.equals("") && !value.equals(" ") && !value.equals("  ")) {
                    if ( name.equals("original_y_lo")) {
                        y.put("lo", value);
                    }
                    if ( name.equals("original_y_hi")) {
                        y.put("hi", value);
                    }
                }
                if ( y.size() > 0 ) {
                    original.put("y", y);
                }                    
            }
            if ( name.contains("z_lo") || name.contains("z_hi") || name.contains("k_lo") || name.contains("k_hi")) {                
                if ( value != null && !value.equals("") && !value.equals(" ") && !value.equals("  ")) {
                    if ( name.equals("original_z_lo")) {
                        z.put("lo", value);
                    }
                    if ( name.equals("original_z_hi")) {
                        z.put("hi", value);
                    }
                    if ( name.equals("original_k_lo") ) {
                        z.put("index_lo", value);
                    }
                    if ( name.equals("original_k_hi") ) {
                        z.put("index_hi", value);
                    }
                }
                if ( z.size() > 0 ) {
                   original.put("z", z);
                }
            }
            if ( name.contains("t_lo") || name.contains("t_hi") || name.contains("l_lo") || name.contains("l_hi") ) {               
                if ( value != null && !value.equals("") && !value.equals(" ") && !value.equals("  ")) {
                    if ( name.contains("original_t_lo")) {
                        t.put("lo", value);
                    }
                    if ( name.contains("original_t_hi")) {
                        t.put("hi", value);
                    }
                    if ( name.equals("original_l_lo") ) {
                        t.put("index_lo", value);
                    }
                    if ( name.equals("original_l_hi") ) {
                        t.put("index_hi", value);
                    }
                }
                if ( t.size() > 0 ) {
                    original.put("t", t);
                }                    
            }
        }    
        return original;
    }
    
    public ArrayList<HashMap> getSections() {
        ArrayList<HashMap> sections = new ArrayList<HashMap>();
        List sectionsElementList = this.getRootElement().getChildren("section");       
        for (Iterator secIt = sectionsElementList.iterator(); secIt.hasNext();) {
            HashMap<String, String> x = new HashMap<String, String>();
            HashMap<String, String> y = new HashMap<String, String>();
            HashMap<String, String> z = new HashMap<String, String>();
            HashMap<String, String> t = new HashMap<String, String>();
            Element sect = (Element) secIt.next();
            HashMap<String, HashMap> section = new HashMap<String, HashMap>();
            List extents = sect.getChildren();
            for (Iterator exIt = extents.iterator(); exIt.hasNext();) {
                Element extent = (Element) exIt.next();
                String name = extent.getName();
                String value = extent.getTextNormalize();
                if ( name.startsWith("x") || name.startsWith("i")) {
                    
                    if ( value != null && !value.equals("") && !value.equals(" ") && !value.equals("  ")) {
                        if ( name.equals("x_lo")) {
                            x.put("lo", value);
                        }
                        if ( name.equals("x_hi")) {
                            x.put("hi", value);
                        }
                        if ( name.equals("i_lo") ) {
                            x.put("index_lo", value);
                        }
                        if ( name.equals("i_hi") ) {
                            x.put("index_hi", value);
                        }
                    }
                    if ( x.size() > 0 ) {
                       section.put("x", x);
                    }
                }
                if ( name.startsWith("y") || name.startsWith("j")) {
                    
                    if ( value != null && !value.equals("") && !value.equals(" ") && !value.equals("  ")) {
                        if ( name.equals("y_lo")) {
                            y.put("lo", value);
                        }
                        if ( name.equals("y_hi")) {
                            y.put("hi", value);
                        }
                        if ( name.equals("j_lo") ) {
                            y.put("index_lo", value);
                        }
                        if ( name.equals("j_hi") ) {
                            y.put("index_hi", value);
                        }
                    }
                    if ( y.size() > 0 ) {
                        section.put("y", y);
                    }                    
                }
                if ( name.startsWith("z") || name.startsWith("k")) {
                    
                    if ( value != null && !value.equals("") && !value.equals(" ") && !value.equals("  ")) {
                        if ( name.equals("z_lo")) {
                            z.put("lo", value);
                        }
                        if ( name.equals("z_hi")) {
                            z.put("hi", value);
                        }
                        if ( name.equals("k_lo") ) {
                            z.put("index_lo", value);
                        }
                        if ( name.equals("k_hi") ) {
                            z.put("index_hi", value);
                        }
                    }
                    if ( z.size() > 0 ) {
                       section.put("z", z);
                    }
                }
                if ( name.startsWith("t") || name.startsWith("l") ) {
                    
                    if ( value != null && !value.equals("") && !value.equals(" ") && !value.equals("  ")) {
                        if ( name.equals("t_lo")) {
                            t.put("lo", value);
                        }
                        if ( name.equals("t_hi")) {
                            t.put("hi", value);
                        }
                        if ( name.equals("l_lo") ) {
                            t.put("index_lo", value);
                        }
                        if ( name.equals("l_hi") ) {
                            t.put("index_hi", value);
                        }
                    }
                    if ( t.size() > 0 ) {
                        section.put("t", t);
                    }                    
                }
            }
            sections.add(section);
        }
        return sections;
    }
    public int getNumberOfSections() {
        List sections = this.getRootElement().getChildren("section");
        return sections.size();
    }
    
    // Probably won't use these since we need to loop the sections.
    public String getX_lo(int section) {
        List sections = this.getRootElement().getChildren("section");
        return ((Element)sections.get(section)).getChildTextTrim("x_lo");
    }
    public String getX_hi(int section) {
        List sections = this.getRootElement().getChildren("section");
        return ((Element)sections.get(section)).getChildTextTrim("x_hi");
    }
    
    public String getY_lo(int section) {
        List sections = this.getRootElement().getChildren("section");
        return ((Element)sections.get(section)).getChildTextTrim("y_lo");
    }
    public String getY_hi(int section) {
        List sections = this.getRootElement().getChildren("section");
        return ((Element)sections.get(section)).getChildTextTrim("y_hi");
    }
    
    public String getZ_lo(int section) {
        List sections = this.getRootElement().getChildren("section");
        return ((Element)sections.get(section)).getChildTextTrim("z_lo");
    }
    public String getZ_hi(int section) {
        List sections = this.getRootElement().getChildren("section");
        return ((Element)sections.get(section)).getChildTextTrim("z_hi");
    }
    
    public String getT_lo(int section) {
        List sections = this.getRootElement().getChildren("section");
        return ((Element)sections.get(section)).getChildTextTrim("t_lo");
    }
    public String getT_hi(int section) {
        List sections = this.getRootElement().getChildren("section");
        return ((Element)sections.get(section)).getChildTextTrim("t_hi");
    }
 

}
