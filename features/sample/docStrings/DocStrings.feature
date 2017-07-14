#
# Copyright 2017 Branko Juric, Brady Wood
# 
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
# 
#     http://www.apache.org/licenses/LICENSE-2.0
# 
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

 Feature: DocStrings

   Scenario: Javascript DocString binding
       Given my value is a function binding
        Then my value should be "true"

   Scenario: Paragraph of text
       Given my paragraph is
             """
             Gwen is a Gherkin interpreter that turns Given-When-Then steps into automation instructions and executes
             them for you so you don't have to do all the programming work. It has an abstracted evaluation engine
             allowing any type of automation capability to be built and mixed in. Meta specifications (also expressed
             in Gherkin) are used to capture automation bindings and allow you to compose step definitions by mapping
             'declarative' steps in features to 'imperative' steps in engines that perform operations.
             """
        Then my paragraph should be
             """
             Gwen is a Gherkin interpreter that turns Given-When-Then steps into automation instructions and executes
             them for you so you don't have to do all the programming work. It has an abstracted evaluation engine
             allowing any type of automation capability to be built and mixed in. Meta specifications (also expressed
             in Gherkin) are used to capture automation bindings and allow you to compose step definitions by mapping
             'declarative' steps in features to 'imperative' steps in engines that perform operations.
             """

   Scenario: Single line of text
       Given my line is
             """
             Gwen is a Gherkin interpreter that turns Given-When-Then steps into automation instructions.
             """
        Then my line should be
             """
             Gwen is a Gherkin interpreter that turns Given-When-Then steps into automation instructions.
             """
         And my line should be "Gwen is a Gherkin interpreter that turns Given-When-Then steps into automation instructions."

   Scenario: Multi paragraph text with interpolation
       Given my attribute 1 is "Given-When-Then"
         And my attribute 2 is "automation"
         And my line is
             """
             Gwen is a Gherkin interpreter that turns ${my attribute 1} steps into ${my attribute 2} instructions and
             executes them for you so you don't have to do all the programming work. It has an abstracted evaluation
             engine allowing any type of ${my attribute 2} capability to be built and mixed in.

             Meta specifications (also expressed in Gherkin) are used to capture ${my attribute 2} bindings and allow
             you to compose step definitions by mapping 'declarative' steps in features to 'imperative' steps in engines
             that perform operations.
             """
        Then my line should be
             """
             Gwen is a Gherkin interpreter that turns Given-When-Then steps into automation instructions and
             executes them for you so you don't have to do all the programming work. It has an abstracted evaluation
             engine allowing any type of automation capability to be built and mixed in.

             Meta specifications (also expressed in Gherkin) are used to capture automation bindings and allow
             you to compose step definitions by mapping 'declarative' steps in features to 'imperative' steps in engines
             that perform operations.
             """

   Scenario: Single line of text with content type
       Given my text line is
             """text
             Gwen is a Gherkin interpreter that turns Given-When-Then steps into automation instructions.
             """
        Then my text line should be
             """text
             Gwen is a Gherkin interpreter that turns Given-When-Then steps into automation instructions.
             """
         And my text line should be "Gwen is a Gherkin interpreter that turns Given-When-Then steps into automation instructions."

   Scenario: Javascript Inline binding
       Given my function value is defined by javascript "function myValue() { return true; } myValue();"
        Then my function value should be "true"
