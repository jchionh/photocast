/**
 * User: jchionh
 * Date: 8/29/13
 * Time: 6:39 PM
 */

// namesapce
pc.states = pc.states || {};

/**
 * initial run state in the stack, does nothing
 * @constructor
 * @extends pc.runstate.BaseRunState
 */
pc.states.InitialState = function() {
    // call the super class init
    pc.runstate.BaseRunState.call(this, pc.runstate.RunFlag.SUSPEND_LOWER);

    this.node = document.createElement('div');
    this.node.innerHTML = 'Whee!!!';
    this.node.setAttribute('class', 'moveable');
    this.nodePos = [0, 0];
    this.speed = 1.5;
    this.node.setAttribute('style', 'left: ' + Math.floor(this.nodePos[0]) + 'px; top: ' + Math.floor(this.nodePos[1]) + 'px;');
};

// extend from GLRunState
pc.utils.extend(pc.states.InitialState, pc.runstate.BaseRunState);

/**
 * onstart
 * @override
 */
pc.states.InitialState.prototype.onStart = function() {
    console.log('InitialSate::onStart');

    pc.gPhotoAreaElement.appendChild(this.node);



};

/**
 * onstop
 * @override
 */
pc.states.InitialState.prototype.onStop = function() {
    console.log('InitialSate::onStop');
};

/**
 * onupdate
 * @param dt
 */
pc.states.InitialState.prototype.onUpdate = function(dt) {
    if (this.nodePos[1] > 720.0 || this.nodePos[1] < 0.0) {
        this.speed = -1.0 * this.speed;
    }

    this.nodePos[0] += this.speed;
    this.nodePos[1] += this.speed;

    this.node.setAttribute('style', 'left: ' + Math.floor(this.nodePos[0]) + 'px; top: ' + Math.floor(this.nodePos[1]) + 'px;');

    var photos = document.getElementsByClassName('moveable');
    for (int i = 0; i < photos.length; ++i) {
        var photo = photos.item(i);
        photo.style.top = Math.floor(this.nodePos[0]) + 'px';
        photo.style.left = Math.floor(this.nodePos[1]) + 'px';
    }

}
