import PlyrJS from "plyr";
import Plyr from "plyr-react";
import "plyr-react/dist/plyr.css";

interface VideoPlayerProps {
  videoSource: string;
}

const VideoPlayer = (props: VideoPlayerProps) => {
  const sourceInfo: PlyrJS.SourceInfo = {
    type: "video",
    sources: [
      {
        src: props.videoSource,
        type: "video/mp4",
      },
    ],
  };

  const options: PlyrJS.Options = {
    controls: [
      "rewind",
      "play",
      "fast-forward",
      "progress",
      "current-time",
      "duration",
      "mute",
      "volume",
      "settings",
      "fullscreen",
    ],
  };

  return <Plyr source={sourceInfo} options={options} />;
};

export default VideoPlayer;
