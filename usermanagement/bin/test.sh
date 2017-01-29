host=$(oc get route --selector="component=${1},application=${2}" --output jsonpath='{$..items[?(@.metadata.name=="'${2}'-app")].spec.host}' --namespace ${2})
if [ -n "${host}" ]; then
   curl -i -v -X GET -H "Content-type: application/json" -H "Accept: application/json" http://${host}${3}
else
   echo "### NO ROUTE TO HOST"
fi
