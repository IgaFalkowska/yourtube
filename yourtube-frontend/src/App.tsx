import React from "react";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import VideoList from "./pages/VideoList";
import VideoDetails from "./pages/VideoDetails";
import NotFound from "./pages/NotFound";
import Layout from "./layout/Layout";

const App = () => {
  return (
    <BrowserRouter>
      <Routes>
        <Route element={<Layout />}>
          <Route path="/" element={<VideoList />} />
          <Route path="/videos/:videoId" element={<VideoDetails />} />
        </Route>
        <Route path="*" element={<NotFound />} />
      </Routes>
    </BrowserRouter>
  );
};

export default App;
