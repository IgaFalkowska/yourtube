import "./Layout.css";

const Header = () => {
  return (
    <header className="header">
      <img className="logo" src={"../logo.png"} alt="logo" />
      <a className="logoTitleLink" href="/">
        YourTube
      </a>
    </header>
  );
};

export default Header;
