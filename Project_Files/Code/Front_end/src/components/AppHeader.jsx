import React from "react";
import { AppBar, Toolbar, Typography } from "@mui/material";

export default function AppHeader() {
  return (
    <AppBar position="static">
      <Toolbar>
        <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
          Custom Metrics Driven Horizontal Pod Autoscaling in Kubernetes
        </Typography>
      </Toolbar>
    </AppBar>
  );
}
