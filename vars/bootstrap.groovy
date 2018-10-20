/* Juju Bootstrap
 *
 * @param cloud
 * @param controller juju controller name
 */
def call(String cloud, String controller) {
    sh "juju bootstrap ${cloud} ${controller}"
}
