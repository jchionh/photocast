/**
 * User: jchionh
 * Date: 8/28/13
 * Time: 12:09 AM
 */

function mainInit() {
    // new our state runner
    pc.gStateRunner = new pc.runstate.StateRunner();

    pc.gPrevTimestamp = 0;
    pc.gDelta = 0;

    pc.gCountAreaElement = document.getElementById('countArea');
    pc.gDeltaTimeAreaElement = document.getElementById('deltaTimeArea');
    pc.gPhotoAreaElement = document.getElementById('photoArea')

    // call our mainloop the first time with a current timestamp
    mainLoop(Date.now());

}

/**
 * this is the mainloop, runs with timestamp
 * @param timestamp
 */
function mainLoop(timestamp) {

    // calculate our delta
    pc.gDelta = Math.max(0.0, timestamp - pc.gPrevTimestamp);
    pc.gPrevTimestamp = timestamp;

    pc.gDeltaTimeAreaElement.innerText = 'dt: ' + pc.gDelta + ' ms';

    window.requestAnimFrame(mainLoop, pc.gPhotoAreaElement);
}
