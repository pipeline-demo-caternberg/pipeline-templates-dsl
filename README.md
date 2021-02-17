# pipeline-templates-dsl

generates an instance of an (dedicated) [CloudBees Pipeline Template Catalog](https://docs.cloudbees.com/docs/admin-resources/latest/pipeline-templates-user-guide/)
see: https://github.com/pipeline-demo-caternberg/pipeline-examples/tree/master/templates/multibranchPipeline

#Pre-requirements
* CloudBees CI Controller 
* Add [this Pipeline Templateb Catalog](https://github.com/pipeline-demo-caternberg/pipeline-examples) to your controller
 * The xample implemementation is related to this [multibranchPipeline template](https://github.com/pipeline-demo-caternberg/pipeline-examples/tree/master/templates/multibranchPipeline)
 * For other template instances the `Seed.groovy` implemenetation needs to be adjusted 
   * `'model'('Pipeline-Tem.c3qk18.log-Examples/multibranchPipeline')`
   * as well as the parameters see `'values'(class: 'tree-map') ` and the related [template.yaml](https://github.com/pipeline-demo-caternberg/pipeline-examples/blob/master/templates/multibranchPipeline/template.yaml) 