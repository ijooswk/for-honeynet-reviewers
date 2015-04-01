/**
 * Dependencies:
 * jquery
 * http://player.youku.com/jsapi
 *
 * Video related scripts
 */

(function () {
    var cliendId = '157b5289ea93196c';

    /**
     * create a youku player
     * @param elemId: the id of the player's div
     * @param videoId
     */
    var createYoukuPlayer = function(elemId, videoId) {
        player = new YKU.Player(elemId, {
            styleid: '0',
            client_id: cliendId,
            vid: videoId
        });
    };
    window.createYoukuPlayer = createYoukuPlayer;
})();
