import VideoPlayer from "../components/VideoPlayer";
import { useEffect, useState } from "react";
import { VideoModel } from "../common/VideoModel";
import { useParams } from "react-router-dom";
import axios from "../common/axios";
import "./VideoDetails.css";
import "../common/CommonStyles.css";
import moment from "moment";

const VideoDetails = () => {
  const { videoId } = useParams();

  const [video, setVideo] = useState<VideoModel>();
  const [loading, setLoading] = useState<boolean>(true);

  useEffect(() => {
    axios
      .get("/videos/" + videoId)
      .then((res) => {
        setVideo(res.data);
      })
      .finally(() => setLoading(false));
  }, [videoId]);

  return (
    <div className="videoDetailsWrapper">
      {loading ? (
        <>Loading...</>
      ) : video == null ? (
        <>Video was not found</>
      ) : (
        <>
          <VideoPlayer
            videoSource={
              "http://localhost:8080/videos/stream/" + video.filename
            }
          />
          <div className="videoTitle">{video.title}</div>
          <div className="videoAuthor">{video.author}</div>
          <div className="videoDate">
            {moment(video.createdOn).format("DD-MM-YYYY HH:mm")}
          </div>
          <div className="videoDescription">{video.description}</div>
        </>
      )}
    </div>
  );
};

export default VideoDetails;
