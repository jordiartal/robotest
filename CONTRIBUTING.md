# HOW TO CONTRIBUTE

## CODE CONVENTIONS AND QUALITY RULES

We establish some code conventions with this tools that we include in the project. 

| TOOL                | VERSION | CONFIG                                                    | COMMENTS                                                                                     | GOALS AND TARGET THRESHOLDS                                                                                                                        |
|---------------------|---------|-----------------------------------------------------------|----------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------|
| Eclipse Preferences | Oxigen+ | `devopsstack-castinfo-eclipse-workspace-preferences.epf`  | Tested in local workstations, try to isolate in a diferent eclipse workspace to avoid errors | Save Actions to format and adapt code and eclipse compiler alert thresholds. Mandatory to be applied and no errors in Eclipse build are acceptable |
| Eclipse Clean Up    | Oxigen+ | `devopsstack-castinfo-eclipse-java-cleanup.xml`           | Tested in local workstations, try to isolate a diferent eclipse workspace to avoid errors    | Clean up options for eclipse. Mandatory to be applied                                                                                              |
| Eclipse Formatter   | Oxigen+ | `devopsstack-castinfo-eclipse-java-formatter.xml`         | Tested in local workstations, try to isolate a diferent eclipse workspace to avoid errors    | Code formatter for eclipse. Mandatory to be applied                                                                                                |
| Checkstyle          | 3.0.0   | robotest BOM                                              |                                                                                              | Mandatory to be applied and no issues                                                                                                              |
| FindBugs            | 3.0.5   | robotest BOM                                              |                                                                                              | Mandatory to be applied and no issues                                                                                                              |
| Sonar               | 4.5.5   | Sonar Way and Findbugs Security plugins                   | Aligned config with other tools                                                              | Mandatory: no bloquer or critical issues and more than 65% test coverage                                                                           |

## DESIGN PRINCIPLES

We try to implement the project we some Clean Code principles and practices:

- KISS (Keep it simple, stupid https://en.wikipedia.org/wiki/KISS_principle)
- DRY (Don't repeat your self https://en.wikipedia.org/wiki/Don%27t_repeat_yourself)
- SSOT (Single Source Of Truth https://en.wikipedia.org/wiki/Single_source_of_truth)
- YAGNI (You aren't gona need it https://en.wikipedia.org/wiki/You_aren%27t_gonna_need_it)
- WABI-SABI (Accept imperfections of code https://spin.atomicobject.com/2016/12/17/software-is-imperfect/)
- SOC (Separation Of Concerns https://en.wikipedia.org/wiki/Separation_of_concerns)
- MODULARITY (High cohesion low coupled modules http://principles-wiki.net/principles:low_coupling http://principles-wiki.net/principles:high_cohesion)
- DEMETERS LAW (https://en.wikipedia.org/wiki/Law_of_Demeter)
- LOD (Law of Demeter (LoD) or principle of least knowledge https://en.wikipedia.org/wiki/Law_of_Demeter)
- STUPID (Singleton, Tight Coupling, Untestability, Premature Optimization, Indescriptive Naming, Duplication, don’t be STUPID: Be GRASP and SOLID https://williamdurand.fr/2013/07/30/from-stupid-to-solid-code/)
- SOLID: SRP (Single responsibility principle), OCP (Open / closed principle), LSP (Liskov substitution principle), ISP (Interface segregation principle), DIP (Dependency inversion principle) https://en.wikipedia.org/wiki/SOLID_(object-oriented_design)
- GRASP (General Responsibility Assignment Software Principles https://en.wikipedia.org/wiki/GRASP_(object-oriented_design))

