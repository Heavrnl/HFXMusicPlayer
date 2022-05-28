package utils;

import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class ResizeHelper {

    public  static final double STAGE_TOP_HEIGHT = 50; //顶部可移动区域
    public  static final double RESIZE_BORDER_SIZE = 15; //可拖拽区域
    public  static final double MINIMUM_WIDTH = 1100; //窗口最小宽度
    public  static final double MINIMUM_HEIGHT = 630; //窗口最小高度


    private static double xOffset = 0;
    private static double StartX = 0;
    private static double OverX = 0;
    private static double LockX = 0;
    private static double yOffset = 0;
    private static double StartY = 0;
    private static double OverY = 0;
    private static double LockY = 0;
    private static double aspectRatio = 0;
    private static Boolean Left = false;
    private static Boolean Horizon = false;
    private static Boolean Top = false;
    private static Boolean Vertical = false;
    private static Boolean H_draggable = false;
    private static Boolean V_draggable = false;
    private static Boolean Movable = false;



    /**
     *
     * @param stage
     * @param proportional 是否等比例放大缩小
     */
    public static void setResize(Stage stage,Boolean proportional) {
        stage.initStyle(StageStyle.TRANSPARENT);
        Scene scene = stage.getScene();
        scene.setOnMouseMoved(event -> {
              if (aspectRatio==0) {
                  aspectRatio = stage.getHeight() / stage.getWidth();
              }
              xOffset = event.getX();
              yOffset = event.getY();
              //左斜
              if ((xOffset < RESIZE_BORDER_SIZE && yOffset < RESIZE_BORDER_SIZE) || (xOffset > stage.getWidth() - RESIZE_BORDER_SIZE && yOffset > stage.getHeight() - RESIZE_BORDER_SIZE)) {
                  scene.setCursor(Cursor.SE_RESIZE);
                  Horizon = true;
                  Vertical = true;
                  if (xOffset < RESIZE_BORDER_SIZE && yOffset < RESIZE_BORDER_SIZE) {
                      Left = true;
                      Top = true;
                  }
              }
              //右斜
              else if ((xOffset > stage.getWidth() - RESIZE_BORDER_SIZE && yOffset < RESIZE_BORDER_SIZE) || (xOffset < RESIZE_BORDER_SIZE && yOffset > stage.getHeight() - RESIZE_BORDER_SIZE)) {
                  scene.setCursor(Cursor.NE_RESIZE);
                  Horizon = true;
                  Vertical = true;
                  if (xOffset > stage.getWidth() - RESIZE_BORDER_SIZE && yOffset < RESIZE_BORDER_SIZE) {
                      Top = true;
                  } else {
                      Left = true;
                  }
              } else if (xOffset < RESIZE_BORDER_SIZE || yOffset < RESIZE_BORDER_SIZE || (xOffset > stage.getWidth() - RESIZE_BORDER_SIZE) || (yOffset > stage.getHeight() - RESIZE_BORDER_SIZE)) {
                  //水平
                  if (xOffset < RESIZE_BORDER_SIZE || (xOffset > stage.getWidth() - RESIZE_BORDER_SIZE)) {
                      scene.setCursor(Cursor.H_RESIZE);
                      if (xOffset < RESIZE_BORDER_SIZE) {
                          Left = true;
                      }
                      Horizon = true;
                  }

                  //垂直
                  else if (yOffset < RESIZE_BORDER_SIZE || (yOffset > stage.getHeight() - RESIZE_BORDER_SIZE)) {
                      scene.setCursor(Cursor.V_RESIZE);
                      if (yOffset < RESIZE_BORDER_SIZE) {
                          Top = true;
                      }
                      Vertical = true;
                  }
              } else {
                  scene.setCursor(Cursor.DEFAULT);
                  Horizon = false;
                  Left = false;
                  Vertical = false;
                  Top = false;
              }
          });

          //获取拖拽起始点
          scene.setOnMousePressed(event -> {
              double x = event.getX();
              double y = event.getY();

              if (x > RESIZE_BORDER_SIZE && x < stage.getWidth() - RESIZE_BORDER_SIZE && y < STAGE_TOP_HEIGHT && y > RESIZE_BORDER_SIZE) {
                  Movable = true;
              }

              if (Horizon) {
                  if (Left) {
                      StartX = stage.getX();
                  } else {
                      StartX = event.getScreenX();
                  }
              }
              if (Vertical) {
                  if (Top) {
                      StartY = stage.getY();
                  } else {
                      StartY = event.getScreenY();
                  }
              }
          });

          scene.setOnMouseReleased(event -> {
              Movable = false;
              StartX = 0;
              StartY = 0;
          });

          scene.setOnMouseDragged(event -> {
              if (Movable) {
                  stage.setX(event.getScreenX() - xOffset);
                  stage.setY(event.getScreenY() - yOffset);
              }

              //当宽小于200时阻止变小
              if (stage.getWidth() <= MINIMUM_WIDTH) {
                  OverX = stage.getX();
                  H_draggable = false;
              } else {
                  H_draggable = true;
              }

              //当高小于200时阻止变小
              if (stage.getHeight() <= MINIMUM_HEIGHT) {
                  OverY = stage.getY();
                  V_draggable = false;
              } else {
                  V_draggable = true;
              }

              //垂直
              if (Vertical) {
                  //顶部拖拽
                  if (Top) {
                      OverY = event.getScreenY();
                      if (V_draggable) {
                          stage.setY(OverY);
                      } else {
                          stage.setY(LockY);
                      }

                      if (StartY < OverY) {
                          if (V_draggable) {
                              stage.setHeight(stage.getHeight() - (OverY - StartY));
                          }
                          if (proportional){
                              stage.setWidth(stage.getHeight()/aspectRatio);
                          }
                      } else {
                          stage.setHeight(stage.getHeight() + (StartY - OverY));
                          if (proportional){
                              stage.setWidth(stage.getHeight()/aspectRatio);
                          }
                      }

                      if (V_draggable) {
                          StartY = event.getScreenY();
                          LockY = event.getScreenY();
                      }
                  }
                  //底部拖拽
                  else {
                      OverY = event.getScreenY();
                      if (StartY < OverY) {
                          stage.setHeight(stage.getHeight() + (OverY - StartY));
                          if (proportional){
                              stage.setWidth(stage.getHeight()/aspectRatio);
                          }
                      } else {
                          if (V_draggable) {
                              stage.setHeight(stage.getHeight() - (StartY - OverY));
                              if (proportional){
                                  stage.setWidth(stage.getHeight()/aspectRatio);
                              }
                          }
                      }
                      StartY = event.getScreenY();
                  }
              }


              if (Horizon) {
                  //左边拖拽
                  if (Left) {
                      OverX = event.getScreenX();
                      if (H_draggable) {
                          stage.setX(OverX);
                      } else {
                          stage.setX(LockX);
                      }

                      if (StartX > OverX) {
                          stage.setWidth(stage.getWidth() + (StartX - OverX));
                          if (proportional){
                              stage.setHeight(stage.getWidth()*aspectRatio);
                          }
                      } else {
                          if (H_draggable) {
                              stage.setWidth(stage.getWidth() - (OverX - StartX));
                              if (proportional){
                                  stage.setHeight(stage.getWidth()*aspectRatio);
                              }
                          }
                      }
                      //每次拖拽结束记住X
                      if (H_draggable) {
                          StartX = event.getScreenX();
                          LockX = event.getScreenX();
                      }
                  }
                  //右边拖拽
                  else {
                      OverX = event.getScreenX();
                      if (StartX < OverX) {
                          stage.setWidth(stage.getWidth() + (OverX - StartX));
                          if (proportional){
                              stage.setHeight(stage.getWidth()*aspectRatio);
                          }
                      } else {
                          if (H_draggable) {
                              stage.setWidth(stage.getWidth() - (StartX - OverX));
                              if (proportional){
                                  stage.setHeight(stage.getWidth()*aspectRatio);
                              }
                          }
                      }
                      StartX = event.getScreenX();
                  }
              }
          });
      }


      public static void setDrag(Stage stage){
        stage.initStyle(StageStyle.TRANSPARENT);
        Scene scene = stage.getScene();
          scene.setOnMousePressed(event -> {
              xOffset = event.getSceneX();
              yOffset = event.getSceneY();
          });

          scene.setOnMouseDragged(event -> {
              stage.setX(event.getScreenX() - xOffset);
              stage.setY(event.getScreenY() - yOffset);
          });
      }


}
