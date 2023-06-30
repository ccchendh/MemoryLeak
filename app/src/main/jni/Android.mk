LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)
#so库的名称
LOCAL_MODULE := nativeheapleak
#需要用到的原材料
LOCAL_SRC_FILES := nativeheapleak.c
include $(BUILD_SHARED_LIBRARY)