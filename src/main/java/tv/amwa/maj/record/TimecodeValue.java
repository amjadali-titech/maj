/*
 * Copyright 2016 Richard Cartwright
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/*
 * $Log: TimecodeValue.java,v $
 * Revision 1.8  2011/01/05 13:09:06  vizigoth
 * Created new forge for making record and union type values.
 *
 * Revision 1.7  2011/01/04 10:40:23  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.6  2009/05/14 16:15:24  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.5  2009/03/30 09:05:00  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.4  2008/01/18 16:18:23  vizigoth
 * Minor comment improvement.
 *
 * Revision 1.3  2008/01/14 20:52:23  vizigoth
 * Changed terminology for interfaces so that they all specify something.
 *
 * Revision 1.2  2007/12/14 15:01:49  vizigoth
 * Added to and edited document comments to a release level.
 *
 * Revision 1.1  2007/11/13 22:13:10  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.record;

import tv.amwa.maj.integer.UInt16;
import tv.amwa.maj.misctype.FrameOffset;

/** 
 * <p>Specifies videotape or audio tape timecode information. The timecode
 * interface is capable of representing timecode values specified by SMPTE&nbsp;12M.</p>
 * 
 * <p>To make values of this type, use the following methods from the 
 * {@linkplain tv.amwa.maj.industry.Forge MAJ forge}:</p>
 * 
 * <ul>
 *  <li>From its underlying component parts of frame count, frames per second and drop
 *  frame flag: 
 *  {@link tv.amwa.maj.industry.Forge#makeTimecode(long, short, boolean)};</li>
 *  <li>Calculated from the SMPTE 12M component values: 
 *  {@link tv.amwa.maj.industry.Forge#calculateTimecode(short, short, short, short, short, boolean)};</li>
 *  <li>Duration calculated from SOM and EOM timecodes: 
 *  {@link tv.amwa.maj.industry.Forge#calculateDuration(TimecodeValue, TimecodeValue)};></li>
 *  <li>End timecode calculated from SOM and duration:
 *  {@link tv.amwa.maj.industry.Forge#calculateEndTimecode(TimecodeValue, TimecodeValue)};</li>
 *  <li>From a string representation as generated by {@link #toString()}: 
 *  {@link tv.amwa.maj.industry.Forge#parseTimecode(String)}.</li>
 * </ul> 
 * 
 * @see tv.amwa.maj.model.TimecodeSegment
 * 
 *
*/
public interface TimecodeValue 
	extends Comparable<TimecodeValue> { 

	/**
	 * <p>Determines whether the timecode is drop (<code>true</code> value) or 
	 * nondrop (<code>false</code> value).</p>
	 * 
	 * <p>If drop is set to <code>true</code>, the real frames-per-second rate represented
	 * by the timecode is 29.97. Calculations of the real time represented by a timecode
	 * or to create a textual representation of the time code drop 108 selected frames
	 * per hour.</p>
	 *
	 * @return Is the timecode a drop value?
	 */
	public boolean getDropFrame();

	/**
	 * <p>Sets whether the timecode is drop (<code>true</code> value) or 
	 * nondrop (<code>false</code> value).</p>
	 *
	 * @param dropFrame Is the timecode a drop value?
	 */
	public void setDropFrame(
			boolean dropFrame);

	/**
	 * <p>Returns the frames per second of the videotape or audio tape of the timecode.</p>
	 *
	 * @return Frames per second of the videotape or audiotape of the timecode.
	 */
	public @UInt16 short getFramesPerSecond();

	/**
	 * <p>Sets the frames per second of the videotape or audio tape of the timecode.</p>
	 *
	 * @param framesPerSecond Frames per second of the videotape or audiotape of the timecode.
	 */
	public void setFramesPerSecond(
			@UInt16 short framesPerSecond);

	/**
	 * <p>Returns the timecode value by the number of frames offset from
	 * the start of the video or audio.</p>
	 *
	 * @return Timecode offset value.
	 */
	public @FrameOffset long getStartTimecode();

	/**
	 * <p>Sets the timecode value by the number of frames offset from the 
	 * start of the video or audio.</p>
	 *
	 * @param offsetFrame Frame offset value to use to set the timecode.
	 */
	public void setStartTimecode(
			@FrameOffset long offsetFrame);
	
	/**
	 * <p>Converts the timecode value to a real time, taking account of the frames 
	 * per second and drop frame values. The value returned is a 
	 * {@link tv.amwa.maj.record.TimeStruct} value, containing the
	 * hours, minutes, seconds and fractions of a second represented by the
	 * frame offset value returned by {@link #getStartTimecode()}.</p>
	 *
	 * @return A time value representing the real time offset of the timecode value.
	 * 
	 * @throws NumberFormatException The timecode value is larger than the range it is 
	 * possible to represent with a time structure value.
	 */
	public TimeStruct convertToRealTime() 
		throws NumberFormatException;
	
	/**
	 * <p>Chronologically compare this timecode value to another one based on real time, 
	 * which is calculated to take account of frames per second and drop frame values.</p>
	 *
	 * @param o Timecode value to compare this value to.
	 * @return A value of&nbsp;-1 if this timecode value is less than the given value,
	 * 0&nbsp;if they are equal and&nbsp;1 if this one is greater than the
	 * one given.
	 * 
	 * @throws NullPointerException The given timecode value to compare to this one is <code>null</code>.
	 */
	public int compareTo(
			TimecodeValue o) 
		throws NullPointerException;
	
	/**
	 * <p>Create a cloned copy of this timecode value.</p>
	 * 
	 * @return Cloned copy of this timecode value.
	 */
	public TimecodeValue clone();
	
	/** 
	 * <p>Formats a timecode value representation in accordance with the SMPTE and EBU specifications.
	 * Specifically:</p>
	 * 
	 * <ul>
	 *  <li>EBU TS N12-1999: "Time-and-control codes for television recording". 1999.</li>
	 *  <li>SMPTE 0012-2008: "SMPTE standard for television, audio and film - time 
	 *  and control code". February 2008. (Character format from SMPTE 0258-2004).</li>
	 * </ul>
	 * 
	 * <p>The format of the returned value is "<code>hh:mm:ss;ff(.p)?</code>" for timecodes with drop
	 * frames and "<code>hh:mm:ss:ff(.p)?</code>" for timecodes with no drop frame. The components of the 
	 * value of the timecode are:</p>
	 * 
	 * <ul>
	 *  <li><code>hh</code> is the number of hours</li>
	 *  <li><code>mm</code> is the number of minutes;</li>
	 *  <li><code>ss</code> is the number of seconds;</li>
	 *  <li><code>ff</code> is the number of frames through this second;</li>.
	 *  <li><code>(.p)?</code> for frame rates over 30, whether the frame is the first (<code>.0</code>) or second 
	 *  (<code>.1</code>) of the pair.</li> 
	 * </ul>
	 * 
	 * @return String representation of this timecode value.
	 * 
	 * @see tv.amwa.maj.industry.Forge#parseTimecode(String)
	 */
	public String toString();
}
