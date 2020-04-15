# MalariaApp
An Android application for detecting parasites.

# Detection Model
Our detection algorithm consists of a Single Shot MultiBox Detector (SSD) that uses a Mobilenet Backbone. The Mobilenet alpha value is equal to 1 (no purning of the network for compression).    

The Tensorflow object detection API was used to train the model.  
The Tensorflow Lite converter was used to convert the trained model to format that is executable on a mobile platform.
Because these tools are publicly available and well documented, we trust that our solution can be easily adopted by Momala. 


For an example of the app in use, see this video
https://www.youtube.com/watch?v=c3U2l9EEw-I

### Commands
To train the model, download and install the Tensorflow object detection API as instructed [here](https://github.com/tensorflow/models/tree/master/research/object_detection). 

After installation, download the model that you would like to train for the Tensorflow object detection model zoo [here](https://github.com/tensorflow/models/blob/master/research/object_detection/g3doc/detection_model_zoo.md). Note that only the SSD models can be converted to the tflite format. We used `ssd_mobilenet_v1_coco`. 

To train the model on your own data, follow the instructions [here](https://github.com/EdjeElectronics/TensorFlow-Object-Detection-API-Tutorial-Train-Multiple-Objects-Windows-10). We trained our models on an Ubuntu 16 desktop with a Titan X GPU.

We created our training data by annotating image in [RectLabel](https://rectlabel.com/) or by converting Momala data to the RectLabel xml format. This Rectlabel xml format could then again be export to COCO json file, which in turn could be converted to a tfrecord file (what tensorflow needs for training) with the command:
```
python3 rectlabel_create_coco_tf_record.py \
    --train_image_dir="Train_Image_Dir" \
    --train_annotations_file="annotations.json" \
    --val_image_dir="Val_Image_Dir" \
     --val_annotations_file="annotations.json" \
    --output_dir="outputdir"
```

After obtaining the training data and making the Mobilenet model's `pipeline.config` file point to the data, the training process was started with: 

```
python3 models/research/object_detection/train.py --logtostderr --train_dir=Training_Directory --pipeline_config_path=Mobilenet_Directory/pipeline.config
```

### Converting The Trained Model
First, the model has to be frozen to an inference graph. This is done with the program `export_tflite_ssd_graph.py`.
Furthermore, a few additional nodes that similfy feeding the model input and fetching the models output are added by this program. 

```
python3  models/research/object_detection/export_tflite_ssd_graph.py    \
  --pipeline_config_path=Mobilenet_Directory/pipeline.config    \
  --trained_checkpoint_prefix=Training_Directory/model.ckpt-checkpoint-number-here    \
  --output_directory=Mobilenet_Directory/output
```

The frozen model (with the .pb format) can now be converted to a Tensorflow lite format by `cd`-ing into the previous command's output directory and executing the following command. Note that the input_shapes argument depends on the model being trained (dimensions can be found in the pipeline.config file). For the standard Mobilenet v1, this is 1,300,300,3.

```

tflite_convert \
--graph_def_file=tflite_graph.pb \
--output_file=detect.tflite \
--input_shapes=1,300,300,3 \
--input_arrays=normalized_input_image_tensor \
--output_arrays='TFLite_Detection_PostProcess','TFLite_Detection_PostProcess:1','TFLite_Detection_PostProcess:2','TFLite_Detection_PostProcess:3' \
--inference_type=FLOAT \
--mean_values=128 \
--std_dev_values=128 \
--allow_custom_ops
```



