/**
 * User: jchionh
 * Date: 8/29/13
 * Time: 5:49 PM
 */

/**
 * Provides requestAnimationFrame in a cross browser way.
 */
window.requestAnimFrame = (function() {
    return window.requestAnimationFrame ||
        window.webkitRequestAnimationFrame ||
        window.mozRequestAnimationFrame ||
        window.oRequestAnimationFrame ||
        window.msRequestAnimationFrame ||
        function(/* function FrameRequestCallback */ callback, /* DOMElement Element */ element) {
            console.log("Falling back from requestAnimationFrame to setTimeout!");
            return window.setTimeout(callback, 1000/60);
        };
})();
