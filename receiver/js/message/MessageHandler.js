/**
 * User: jchionh
 * Date: 9/30/13
 * Time: 2:08 AM
 */

// namespace
pc.message = pc.message || {};

/**
 * Message handler implements onChannelOpened, onChannelClosed, onMessage
 * to handle messages from the sender
 * @constructor
 */
pc.message.MessageHandler = function() {

};

pc.message.MessageHandler.prototype.onChannelOpened = function(event) {
    var messageArea = document.getElementById('messageArea');
    messageArea.innerHTML = 'onChannelOpened';

};

pc.message.MessageHandler.prototype.onChannelClosed = function(event) {
    var messageArea = document.getElementById('messageArea');
    messageArea.innerHTML = 'onChannelOpened';
};

pc.message.MessageHandler.prototype.onMessage = function(event) {

    var message = event.message;
    var channel = event.target;

    var messageArea = document.getElementById('messageArea');
    messageArea.innerHTML = JSON.stringify(message);
};