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
    // create our channelhandler
    this.channelHandler = new cast.receiver.ChannelHandler('PhotocastDebug');
    // and register our message handlers
    this.channelHandler.addEventListener(cast.receiver.Channel.EventType.MESSAGE, this.onMessage.bind(this));
    this.channelHandler.addEventListener(cast.receiver.Channel.EventType.OPEN, this.onChannelOpened.bind(this));
    this.channelHandler.addEventListener(cast.receiver.Channel.EventType.CLOSED, this.onChannelClosed.bind(this));
};

/**
 * use the receiver app to create a channel factory and add it to our channel handler
 * to setup communications
 * @param {cast.receiver.Receiver} receiverApp
 */
pc.message.MessageHandler.prototype.setupChannelFactory = function(receiverApp) {
    var channelFactory = receiverApp.createChannelFactory(pc.chromecast.CAST_PROTOCOL);
    this.channelHandler.addChannelFactory(channelFactory);
}

/**
 * called when message is received from the sender
 * @param event
 */
pc.message.MessageHandler.prototype.onMessage = function(event) {
    var message = event.message;
    var channel = event.target;
    console.log('********onMessage********' + JSON.stringify(message));

    // now hack an image and put it on screen
    var image = new Image();
    image.src = 'http://jchionh-photocast-images.s3-website-us-east-1.amazonaws.com/tos_256.jpg';
    image.width = 256;
    image.height = 256;
    image.style.position = 'absolute';
    image.style.left = Math.floor((Math.random() * 1280)) + 'px';
    image.style.top = Math.floor((Math.random() * 720)) + 'px';
    image.id = 'testImage';

    var photoArea = document.getElementById('photoArea');
    photoArea.appendChild(image);

};

/**
 * when channel is opened
 * @param event
 */
pc.message.MessageHandler.prototype.onChannelOpened = function(event) {
    console.log('onChannelOpened. Total number of channels: ' + this.channelHandler.getChannels().length);
};


/**
 * when channel is closed
 * @param event
 */
pc.message.MessageHandler.prototype.onChannelClosed = function(event) {
    console.log('onChannelClosed. Total number of channels: ' + this.channelHandler.getChannels().length);
    if (this.channelHandler.getChannels().length == 0) {
        window.close();
    }
};

