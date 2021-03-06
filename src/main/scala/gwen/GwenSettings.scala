/*
 * Copyright 2014-2020 Branko Juric, Brady Wood
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

import gwen.dsl.AssertionMode
import gwen.dsl.BehaviorRules
import gwen.dsl.FeatureMode
import gwen.dsl.StateLevel
import gwen.Errors._

/**
  * Provides access to gwen settings defined through system properties loaded 
  * from properties files.
  *
  * @author Branko Juric
  */
object GwenSettings {

  /** 
   * Cap max threads to number of avilable processors.
   */
  val availableProcessors = Runtime.getRuntime().availableProcessors()
  
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
    * Enabling this feature will exit execution when the first failure is detected.
    */
  def `gwen.feature.failfast.exit`: Boolean = Settings.getOpt("gwen.feature.failfast.exit").getOrElse("false").toBoolean
  
  /**
   * Provides access to the `gwen.report.slideshow.framespersecond` property setting
   * used to set the default frame per second (speed) of the slideshow (default value is 4).
   */
  def `gwen.report.slideshow.framespersecond`: Int = Settings.getOpt("gwen.report.slideshow.framespersecond").map(_.toInt).getOrElse(4)
  
  /**
    * Provides access to the `gwen.report.overwrite` property setting used to overwrite 
    * or create backups of previously generated reports (default value is `false`). 
    * Enabling this feature will create timestamped backups of previous reports. 
    */
  def `gwen.report.overwrite`: Boolean = Settings.getOpt("gwen.report.overwrite").getOrElse("false").toBoolean
  
  /**
   * Provides access to the `gwen.rampup.interval.seconds` property setting used
   * to set the ramp up interval (in seconds) for staggering parallel feature executions  
   * (this setting is optional and only used in for parallel execution options).
   */
  def `gwen.rampup.interval.seconds`: Option[Int] = Settings.getOpt("gwen.rampup.interval.seconds").map(_.toInt)
  
  /**
    * Provides access to the `gwen.report.suppress.meta` setting used to control whether 
    * or not meta report generation will be suppressed (default value is `false`).
    */
  def `gwen.report.suppress.meta`: Boolean = Settings.getOpt("gwen.report.suppress.meta").getOrElse("false").toBoolean

  /**
    * Controls whether or not slideshows should be generated in HTML reports. This property is implicitly set to true
    * in the web engine only if screenshot capturing is enabled (gwen.web.capture.screenshots=true in web engine settings).
    * Users should not explicitly set this value.
    */
  def `gwen.report.slideshow.create` = Settings.getOpt("gwen.report.slideshow.create").getOrElse("false").toBoolean

  /**
    * Provides access to the `gwen.auto.discover.meta` property setting used to enable
    * or disable automatic discovery of meta files (default value is `true` for enabled).
    * Disabling this will prevent Gwen from automatically discovering and loading meta files in the path of an
    * executing feature, forcing the user to control explicitly through the -m/--meta command line option which meta
    * files to load.
    */
  def `gwen.auto.discover.meta`: Boolean = Settings.getOpt("gwen.auto.discover.meta").getOrElse("true").toBoolean

  /**
    * Provides access to the `gwen.auto.discover.data.csv` property setting used to enable
    * or disable automatic discovery of CSV data files (default value is `true` for enabled).
    * Disabling this will prevent Gwen from automatically discovering and loading CSV files in the path of an executing
    * feature, forcing the user to control explicitly through the -i/--input command line option which CSV files to load.
    */
  def `gwen.auto.discover.data.csv`: Boolean = Settings.getOpt("gwen.auto.discover.data.csv").getOrElse("true").toBoolean

  /**
    * Provides access to the `gwen.assertion.mode` property setting used to enable hard, soft, or sustained
    * assertions (default value is `hard`).
    *   - `hard` - Halts processing on first assertion failure
    *   - `soft` - Collects all assertion failures and continues processing
    *   - `sustained` - Collects all assertion failures and continues processing without raising failure
    */
  def `gwen.assertion.mode`: AssertionMode.Value =
    Settings.getOpt("gwen.assertion.mode").map(_.toLowerCase).map(AssertionMode.withName).getOrElse(AssertionMode.hard)

  /**
    * Provides access to the `gwen.state.level` property setting used to control if state is maintained
    * at the scenario or feature level during execution.
    *   - `feature` - Feature level state is shared across scenarios (default)
    *   - `scenario` - Each scenario gets a new state which is not shared across scenarios
    */
  def `gwen.state.level`: StateLevel.Value =
    Settings.getOpt("gwen.state.level").map(_.toLowerCase).map(StateLevel.withName).getOrElse(StateLevel.feature)

  /**
    * Provides access to the `gwen.feature.mode` property setting used to determine whether the
    * feature mode is declarative or imperative (default value is `imperative`). When declarative,
    * the DSL steps defined in the Gwen engine cannot be used directly in features and must be 
    * bound to step definitions defined in meta instead. This forces the user to write features 
    * that are clean and free of automation concerns. When imperative, then DSL steps can be used 
    * directly in features.
    */
    def `gwen.feature.mode`: FeatureMode.Value = 
      Settings.getOpt("gwen.feature.mode").map(_.toLowerCase).map(FeatureMode.withName).getOrElse(FeatureMode.imperative)

    /**
    * Provides access to the `gwen.associative.meta` property setting used to control whether or 
    * not meta files having the same name (excluding file extension) and same location as feature 
    * files are only loaded for that feature and loaded last (default value is false).
    * This setting is only honoured if `gwen.auto.discover.meta` is also enabled.
    */
  def `gwen.associative.meta`: Boolean = 
    `gwen.auto.discover.meta` && Settings.getOpt("gwen.associative.meta").getOrElse("false").toBoolean

  /**
    * Provides access to the `gwen.behavior.rules` property setting used to determine whether strict, 
    * or lenient rules around Given-When-Then usage should be enforced in features (default value is 
    * `lenient`). When strict, scenarios and backgrounds must contain Given-When-Then ordered steps 
    * and Given steps set context, When steps must perform actions, and Then or But steps must perform 
    * assertions. When `leneient` no behavioral rules are enforced. Not that `gwen.behaviour.rules` is 
    * an alias for this setting.
    */
    def `gwen.behavior.rules`: BehaviorRules.Value = 
      Settings.getOpt("gwen.behavior.rules").orElse(Settings.getOpt("gwen.behaviour.rules")).map(_.toLowerCase).map(BehaviorRules.withName).getOrElse(BehaviorRules.lenient)

  /**
    * Provides access to the `gwen.feature.dialect` property setting used to set the default 
    * dialect for Gherkin keywords in features.
    */
    def `gwen.feature.dialect`: String = Settings.getOpt("gwen.feature.dialect").getOrElse("en")

    /**
      * Provides access to the `gwen.parallel.maxThreads` property setting used to set the maximum number
      * of threads to use in parallel execution mode. The value will default to the 
      * number of avialable processors in the host environment if it is not specified or exceeds
      * that value.
      */
    def `gwen.parallel.maxThreads`: Int = {
      Settings.getOpt("gwen.parallel.maxThreads").map(_.toInt).map { maxThreads =>
        if (maxThreads < 1) {
          propertyLoadError("gwen.parallel.maxThreads", "cannot be less than 1")
        } else if (maxThreads > availableProcessors) {
          availableProcessors
        } else {
          maxThreads
        }
      } getOrElse { 
        availableProcessors
      }
    }

    /** 
     * Provides access to the character used to mask settings defined with the `:masked` suffix.
     * Default value is `●`.
     */
    def `gwen.mask.char`: Char = {
      Settings.getOpt("gwen.mask.char") map { maskChar => 
        if (maskChar.length != 1) {
          Errors.invalidSettingError("gwen.mask.char", maskChar, "Mask character length must be 1")
        } else {
          maskChar(0)
        }
      } getOrElse('●')
    }

}
