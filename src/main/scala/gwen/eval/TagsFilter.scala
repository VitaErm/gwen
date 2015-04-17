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

package gwen.eval

import gwen.dsl.FeatureSpec
import gwen.dsl.Tag
import gwen.Predefs.Kestrel

/**
  * Checks that a feature satisfies all user provided include/exclude tags.
  * Features that do not satisfy any of the provided tags are filtered out 
  * and returned as None types. Features having scenarios that satisfy all 
  * tags are pruned of all scenarios that do not and are wrapped and 
  * returned as Some types.
  * 
  * @author Branko Juric
  */
object TagsFilter {

  private val DefaultTags = List((Tag("Ignore"), false))
  
  /**
    * Filters a feature using the given include/exclude tag filters.
    * 
    * @param spec the parsed feature spec to check
    * @param tagFilters user provided tag filters (includes:(tag, true) and excludes:(tag, false))
    * @return None if the given feature does not have any scenarios that satisfy all tags; 
    *         Some otherwise (with only the scenarios that do)
    */
  def filter(spec: FeatureSpec, tagFilters: List[(Tag, Boolean)]): Option[FeatureSpec] = { 
    val filters = tagFilters ++ DefaultTags
    println(filters)
    spec.scenarios flatMap { scenario =>
      val effectiveTags = spec.feature.tags ++ scenario.tags
      val allSatisfied = filters.foldLeft(true) { 
        case (satisfied, (tag, include)) =>
          val hasTag = effectiveTags.contains(tag)
          satisfied && ((include && hasTag) || (!include && !hasTag))
      }
      if (allSatisfied) Some(scenario)
      else None
    } match {
      case Nil => None
      case scenarios => Some(FeatureSpec(spec.feature, spec.background, scenarios, None, Nil))
    }
  }
  
}