/* Generate a uuid section for build ids
 */

def call() {
    def rid = UUID.randomUUID().toString().split('-')
    return rid.first()
}
