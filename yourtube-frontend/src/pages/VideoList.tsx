import axios from "../common/axios";
import { ChangeEvent, useEffect, useState } from "react";
import { VideoModel } from "../common/VideoModel";
import VideoTile from "../components/VideoTile";
import "./VideoList.css";
import useDebounce from "../common/useDebounce";

const VideoList = () => {
  const [allVideos, setAllVideos] = useState<VideoModel[]>([]);
  const [videosToShow, setVideosToShow] = useState<VideoModel[]>([]);
  const [searchTerm, setSearchTerm] = useState<string>("");
  const debouncedSearchTerm: string = useDebounce<string>(searchTerm, 500);

  useEffect(() => {
    axios.get("/videos").then((res) => {
      setAllVideos(res.data);
      setVideosToShow(res.data);
    });
  }, []);

  const handleSearchChange = (event: ChangeEvent<HTMLInputElement>) => {
    setSearchTerm(event.target.value);
  };

  useEffect(() => {
    const lowerCaseSearchTerm = debouncedSearchTerm.toLowerCase();
    const filteredVideos = allVideos.filter((video) =>
      shouldShowVideo(lowerCaseSearchTerm, video)
    );
    setVideosToShow(filteredVideos);
  }, [debouncedSearchTerm, allVideos]);

  const shouldShowVideo = (lowerCaseSearchTerm: string, video: VideoModel) => {
    return (
      video.title.toLowerCase().includes(lowerCaseSearchTerm) ||
      video.author.toLowerCase().includes(lowerCaseSearchTerm) ||
      video.tags.some((tag) => tag.toLowerCase().includes(lowerCaseSearchTerm))
    );
  };

  return (
    <>
      <div className="searchWrapper">
        <input
          className="searchInput"
          type="input"
          placeholder="Search"
          value={searchTerm}
          onChange={handleSearchChange}
        />
      </div>
      <div className="videosWrapper">
        {videosToShow.map((video) => (
          <VideoTile video={video} key={video.id} />
        ))}
      </div>
    </>
  );
};

export default VideoList;
