
from __future__ import print_function

import numpy as np
import cv2 as cv
import socket
import sys
import pickle
import struct ### new code
from common import clock, draw_str

cam = cv.VideoCapture(0)
cascade_fn = "model/haarcascade_frontalface_alt.xml"
nested_fn = "model/haarcascade_eye.xml"

cascade = cv.CascadeClassifier(cascade_fn)
nested = cv.CascadeClassifier(nested_fn)

def detect(img, cascade):
    rects = cascade.detectMultiScale(img, scaleFactor=1.3, minNeighbors=4, minSize=(30, 30),
                                     flags=cv.CASCADE_SCALE_IMAGE)
    if len(rects) == 0:
        return []
    rects[:,2:] += rects[:,:2]
    return rects

def draw_rects(img, rects, color):
    for x1, y1, x2, y2 in rects:
        cv.rectangle(img, (x1, y1), (x2, y2), color, 2)
def run_webcam():
    clientsocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    clientsocket.connect(('192.168.1.20', 8089))
    while True:
        ret_val, img = cam.read()
        ret, img = cam.read()
        gray = cv.cvtColor(img, cv.COLOR_BGR2GRAY)
        gray = cv.equalizeHist(gray)
        t = clock()
        rects = detect(gray, cascade)
        vis = img.copy()
        draw_rects(vis, rects, (0, 255, 0))
        if not nested.empty():
            for x1, y1, x2, y2 in rects:
                roi = gray[y1:y2, x1:x2]
                vis_roi = vis[y1:y2, x1:x2]
                subrects = detect(roi.copy(), nested)
                draw_rects(vis_roi, subrects, (255, 0, 0))
        dt = clock()
        draw_str(vis, (20, 20), 'time: %.1f ms' % (dt * 1000))
        data = pickle.dumps(vis)
        clientsocket.sendall(struct.pack("L", len(data))+data)
        cv.imshow('yüz tanımaca', vis)

        if cv.waitKey(1) == 27:
            break  # esc to quit
    cv.destroyAllWindows()


def main():
    run_webcam()


if __name__ == '__main__':
    main()
