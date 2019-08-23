package jpos.profile;

///////////////////////////////////////////////////////////////////////////////
//
// This software is provided "AS IS".  The JavaPOS working group (including
// each of the Corporate members, contributors and individuals)  MAKES NO
// REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF THE SOFTWARE,
// EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED 
// WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND 
// NON-INFRINGEMENT. The JavaPOS working group shall not be liable for
// any damages suffered as a result of using, modifying or distributing this
// software or its derivatives. Permission to use, copy, modify, and distribute
// the software and its documentation for any purpose is hereby granted. 
//
// The JavaPOS Config/Loader (aka JCL) is now under the CPL license, which 
// is an OSS Apache-like license.  The complete license is located at:
//    http://oss.software.ibm.com/developerworks/opensource/license-cpl.html
//
///////////////////////////////////////////////////////////////////////////////

/**
 * Defines a JposEntry property info which is a set of properties information
 * for a JposEntry
 * @since 1.3 (SF 2K meeting)
 * @author E. Michael Maximilien (maxim@us.ibm.com)
 */
public interface PropInfo
{
	//-------------------------------------------------------------------------
	// Public methods
	//

	/** @return the name of this property */
	public String getName();

	/** @return the type of this property */
	public PropType getType();

	/** @return the default value for this property */
	public PropValue getDefaultValue();

	/** @return the list of possible values for this property */
	public PropValueList getValues();

	/** @return the DevCat that this property can be applied to */
	public DevCat getDevCat();

	/** @return the PropInfoViewer for this property */
	public PropInfoViewer getViewer();

	/** @return the Profile associated with this PropInfo */
	public Profile getProfile();

	/** @return a description of this PropInfo (this can be HTML formated) */
	public String getDescription();

	/** @return a short description of this PropInfo (this can be HTML formated) */
	public String getShortDescription();
}
