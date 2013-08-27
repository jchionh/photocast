/**
 * User: jchionh
 * Date: 2/20/13
 * Time: 11:40 PM
 */
// create namespace
pc.utils = pc.utils || {};

/**
 * implement the extend using prototype inheritance extend method
 * @param {Object} newObject the derived object
 * @param {Object} baseObject the base object to derive from
 */
pc.utils.extend = function(newObject, baseObject) {
    newObject.prototype = new baseObject();
    newObject.prototype.constructor = newObject;
};