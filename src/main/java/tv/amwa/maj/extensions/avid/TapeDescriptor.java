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

package tv.amwa.maj.extensions.avid;

import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.integer.Int32;

public interface TapeDescriptor
	extends tv.amwa.maj.model.TapeDescriptor {

	public @Int32 int getColorFrame()
		throws PropertyNotPresentException;

	public void setColorFrame(
			@Int32 Integer colorFrame);

}
