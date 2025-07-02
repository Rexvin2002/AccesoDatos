import React from "react";
import Card from "@mui/material/Card";
import CardMedia from "@mui/material/CardMedia";
import CardContent from "@mui/material/CardContent";
import Typography from "@mui/material/Typography";
import CardActions from "@mui/material/CardActions";
import Button from "@mui/material/Button";

const MiCard: React.FC = () => {
  return (
    <Card className="card">
      <CardMedia
        component="img"
        className="card-media"
        image="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSdI5h6LZxis-xvMA-mioIFBUdBqrofceIn1A&s"
        alt="Placeholder Image"
      />
      <CardContent className="card-content">
        <Typography variant="h5" className="card-title">
          Card Title
        </Typography>
        <Typography variant="body2" className="card-description">
          This is some example content for the card.
        </Typography>
      </CardContent>
      <CardActions className="card-actions">
        <Button className="card-button" size="small">
          Share
        </Button>
        <Button className="card-button" size="small">
          Learn More
        </Button>
      </CardActions>
    </Card>
  );
};

export default MiCard;
