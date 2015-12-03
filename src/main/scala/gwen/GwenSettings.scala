/*
 * Copyright 2014-2015 Branko Juric, Brady Wood
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package gwen

/**
  * Provides access to gwen settings defined through system properties loaded 
  * from properties files.
  *
  * @author Branko Juric
  */
object GwenSettings {
  
  /**
    * Provides access to the `gwen.feature.failfast` property setting used to enable 
    * or disable fail fast mode at the feature level (default value is `true`). 
    * Enabling this feature will fail a feature as soon as the first scenario in that 
    * feature fails.  Other features (if provided) will resume.
    */
  def `gwen.feature.failfast`: Boolean = Settings.getOpt("gwen.feature.failfast").getOrElse("true").toBoolean
  
  /**
    * Provides access to the `gwen.feature.failfast.exit` property setting used to exit all execution 
    * on first failure (default value is `false`). 
    * Enabling this feature will exit exceution when the first failure is detected. 
    */
  def `gwen.feature.failfast.exit`: Boolean = Settings.getOpt("gwen.feature.failfast.exit").getOrElse("false").toBoolean
  
  /**
   * Provides access to the `gwen.report.slideshow.framespersecond` property setting
   * used to set the default frame per second (speed) of the slideshow (default value is 1).
   */
  def `gwen.report.slideshow.framespersecond`: Int = Settings.getOpt("gwen.report.slideshow.framespersecond").map(_.toInt).getOrElse(1)
  
}