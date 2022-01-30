import { VideoModel } from "../common/VideoModel";
import "./VideoTile.css";
import "../common/CommonStyles.css";
import { useNavigate } from "react-router-dom";
import moment from "moment";

export interface VideoComponentProps {
  video: VideoModel;
}

const VideoTile = (props: VideoComponentProps) => {
  const { video } = props;
  const navigate = useNavigate();

  const redirectToVideoDetails = () => {
    navigate("/videos/" + video.id);
  };

  return (
    <div className="videoWrapper">
      <img
        className="videoImage"
        alt="video"
        src={"data:image/PNG;base64, " + video.encryptedImage}
        onClick={redirectToVideoDetails}
      />
      <div className="videoTitle">{video.title}</div>
      <div className="videoAuthor">{video.author}</div>
      <div className="videoDate">
        {moment(video.createdOn).format("DD-MM-YYYY HH:mm")}
      </div>
    </div>
  );
};

export default VideoTile;
