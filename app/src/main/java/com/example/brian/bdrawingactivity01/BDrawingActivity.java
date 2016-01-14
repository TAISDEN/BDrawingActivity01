package com.example.brian.bdrawingactivity01;
//################################################################################################
// Written By: Brian Bolinder
// Date started: 7/12/15
//================================================================================================
// Description:
// - A test program to make a drawing app
//------------------------------------------------------------------------------------------------
// Progress:
// - 7/12/15
//   Started the project
//   Made the UI layout
//   Made the dialog that lets the user choose the color they want to use
// - 7/13/15
//   Made the color choosen by the user in the dialog display on the button that brings up the
//    dialog
// - 7/14/15
//   Changed some code to simplify
//   Created the brushsize_chooser dialog
//   !- Couldnt get the xsmall size to look right in the dialog
// - 7/21/15
//   Got the xsmall brush size to look right
// - 8/11/15
//   Starting to make the drawing view
// - 8/30/15
//   Working on the DrawingView to make it so the user can draw
//   - need to make the user able to choose the brush size.
// - 8/31/15
//   Made the brush chooser work
//   MADE THE IT SO THE USER CAN PAINT!!!
//   - Somehow when the user chooses black or chooses a different color after useing black
//     the draw screen clears.
// - 9/7/15
//   Fixed the screen so it doesnt erase when the user chooses black.
//   - It was part of the test text it changed the size of the draw screen and made it redraw and
//     clear the screen
//   Added the erase feature.
//   - it doesnt seem to stop eraseing
// - 9/15/15
//   Trying to make erase work by changing the color to white
// - 12/22/15
//   GOT IT WORKING AGAIN AFTER REINSTALLING ANDROID STUDIO AGAIN AND CHANGING FILE NAMES.
//   Erase seems to be working.
//   Changes the color to the background color to erase.
// - 1/5/15
//   -Added the new button funtion that has a dialog that lets the user confirm that they want
//   to start a new drawing before it clears.
//   -Tried to add the save function, didnt work.
// - 1/12/16
//   -Tried to fix the save button but didnt work.
//################################################################################################

//- IMPORTS: -------------------------------------------------------------------------------------

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.UUID;
//------------------------------------------------------------------------------------------------

//################################################################################################
public class BDrawingActivity extends Activity
    {
    //============================================================================================
    //- VARIABLES:
    private TextView testDisplay;

    private Dialog chooseColorDLG;
    private Dialog brushChooserDLG;

    private float xsmallBrush, smallBrush, mediumBrush, largeBrush;
    //private int currColor = 0xff000000; //-BLACK AS THE DEFAULT COLOR
    private ImageButton colorChooseBTN;

    private DrawingView drawView;

    private boolean erase = false;

    //============================================================================================
    @Override
    protected void onCreate(Bundle savedInstanceState)
        {
        //---------------------------------------------------------------------------------------
        //-NORMAL STUFF
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bdrawing);
        //---------------------------------------------------------------------------------------
        testDisplay = (TextView)
                findViewById(R.id.testDisp);

        colorChooseBTN = (ImageButton)
                findViewById(R.id.PaintColor);

        drawView = (DrawingView) findViewById(R.id.drawing);

        xsmallBrush = getResources().getInteger(R.integer.xsmall_size);
        smallBrush = getResources().getInteger(R.integer.small_size);
        mediumBrush = getResources().getInteger(R.integer.medium_size);
        largeBrush = getResources().getInteger(R.integer.large_size);
        //-SET THE DEFAULT BRUSH SIZE TO MEDIUM
        drawView.setBrushSize(mediumBrush);
        }

    //============================================================================================
    //-WHEN THE USER PRESSES THE COLOR BUTTON SHOW THE AVAILABLE COLORS...
    public void ChooseColor(View v)
        {
        //-SHOW THE COLOR_CHOOSER LAYOUT DIALOG
        chooseColorDLG = new Dialog(this);
        chooseColorDLG.setTitle("Choose Color:");
        chooseColorDLG.setContentView(R.layout.color_chooser);
        chooseColorDLG.show();
        //-----------------------------------------------------------------------------------------
        //-SETUP THE COLOR BUTTONS THAT THE USER CAN CHOOSE FROM
        ImageButton color1Btn = (ImageButton) chooseColorDLG.findViewById(R.id.color1);
        ImageButton color2Btn = (ImageButton) chooseColorDLG.findViewById(R.id.color2);
        ImageButton color3Btn = (ImageButton) chooseColorDLG.findViewById(R.id.color3);
        ImageButton color4Btn = (ImageButton) chooseColorDLG.findViewById(R.id.color4);
        //-----------------------------------------------------------------------------------------
        //-USER CHOSE THE 1ST COLOR (BLACK)
        color1Btn.setOnClickListener(new View.OnClickListener()
        {
        @Override
        public void onClick(View vcb)
            {
            //DisplayTestText("Black");
            chooseColorDLG.dismiss();
            ColorSelected(vcb);
            }
        });
        //-USER CHOSE THE 2ND COLOR (RED)
        color2Btn.setOnClickListener(new View.OnClickListener()
        {
        @Override
        public void onClick(View vcb)
            {
            //DisplayTestText("Red");
            chooseColorDLG.dismiss();
            ColorSelected(vcb);
            }
        });
        //-USER CHOSE THE 3RD COLOR (BLUE)
        color3Btn.setOnClickListener(new View.OnClickListener()
        {
        @Override
        public void onClick(View vcb)
            {
            //DisplayTestText("Blue");
            chooseColorDLG.dismiss();
            ColorSelected(vcb);
            }
        });
        //-USER CHOSE THE 4TH COLOR (GREEN)
        color4Btn.setOnClickListener(new View.OnClickListener()
        {
        @Override
        public void onClick(View vcb)
            {
            //DisplayTestText("Green");
            chooseColorDLG.dismiss();
            ColorSelected(vcb);
            }
        });
        }

    //--------------------------------------------------------------------------------------------
    //-USER CHOOSING THE COLOR THEY WANT...
    public void ColorSelected(View v)
        {
        String chosenColor = v.getTag().toString();
        drawView.setColor(chosenColor);
        //DisplayTestText(chosenColor);
        colorChooseBTN.setBackground(v.getBackground());
        if (erase) erase = false;
        //chooseColorDLG.hide();
        }

    //============================================================================================
    //-WHEN THE USER PRESSES THE BRUSH BUTTON SHOW THE AVAILABLE SIZES...
    public void ChooseBrushSize(View v)
        {
        brushChooserDLG = new Dialog(this);

        if (!erase) brushChooserDLG.setTitle("Choose Brush Size:");
        else brushChooserDLG.setTitle("Erase Size:");

        brushChooserDLG.setContentView(R.layout.brushsize_chooser);
        brushChooserDLG.show();
        //-----------------------------------------------------------------------------------------
        //- SETUP THE BRUSH SIZE BUTTONS THAT THE USER CAN CHOOSE FROM
        ImageButton sizeBtn1 = (ImageButton) brushChooserDLG.findViewById(R.id.xsmall_brush);
        ImageButton sizeBtn2 = (ImageButton) brushChooserDLG.findViewById(R.id.small_brush);
        ImageButton sizeBtn3 = (ImageButton) brushChooserDLG.findViewById(R.id.medium_brush);
        ImageButton sizeBtn4 = (ImageButton) brushChooserDLG.findViewById(R.id.large_brush);
        //-----------------------------------------------------------------------------------------
        //-USER CHOSE THE XSMALL BRUSH
        sizeBtn1.setOnClickListener(new View.OnClickListener()
        {
        @Override
        public void onClick(View vsb)
            {
            drawView.setBrushSize(xsmallBrush);
            if (!erase) drawView.setLastBrushSize(xsmallBrush);
            brushChooserDLG.dismiss();
            }
        });
        //-USER CHOSE THE SMALL BRUSH
        sizeBtn2.setOnClickListener(new View.OnClickListener()
        {
        @Override
        public void onClick(View vsb)
            {
            drawView.setBrushSize(smallBrush);
            if (!erase) drawView.setLastBrushSize(smallBrush);
            brushChooserDLG.dismiss();
            }
        });
        //-USER CHOSE THE MEDIUM BRUSH
        sizeBtn3.setOnClickListener(new View.OnClickListener()
        {
        @Override
        public void onClick(View vsb)
            {
            drawView.setBrushSize(mediumBrush);
            if (!erase) drawView.setLastBrushSize(mediumBrush);
            brushChooserDLG.dismiss();
            }
        });
        //-USER CHOSE THE LARGE BRUSH
        sizeBtn4.setOnClickListener(new View.OnClickListener()
        {
        @Override
        public void onClick(View vsb)
            {
            drawView.setBrushSize(largeBrush);
            if (!erase) drawView.setLastBrushSize(largeBrush);
            brushChooserDLG.dismiss();
            }
        });
        }

    //============================================================================================
    //-ERASE BUTTON
    public void EraseButton(View v)
        {
        if (!erase)
            {
            erase = true;
            drawView.setErase(true);
            } else
            {
            erase = false;
            drawView.setErase(false);
            }
        }

    //============================================================================================
    //-NEW BUTTON
    /*
    STARTED - 1/5/16
    made the onClick method
    has a dialog that lets the user confirm that they want to clear the drawing screen and
    start a new one
     */
    public void NewButton(View v)
        {
        AlertDialog.Builder newDialog = new AlertDialog.Builder(this);
        newDialog.setTitle("New drawing");
        newDialog.setMessage("Start new drawing (you will lose the current drawing)?");
        newDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener()
        {
        public void onClick(DialogInterface dialog, int which)
            {
            drawView.startNew();
            dialog.dismiss();
            }
        });
        newDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
        {
        public void onClick(DialogInterface dialog, int which)
            {
            dialog.cancel();
            }
        });
        newDialog.show();
        }
    //=============================================================================================
    //-SAVE BUTTON
    /*
    STARTED - 1/5/16
    Made the onClick for the save button that gives the user a dialog that lets them confirm if
    they want to save the picture
    --- 1/12/16
    Trying to fix the save function.
     */
    public void SaveButton(View v)
        {
        AlertDialog.Builder saveDialog = new AlertDialog.Builder(this);
        saveDialog.setTitle("Save drawing");
        saveDialog.setMessage("Save drawing to device Gallery?");
        saveDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener()
        {
        public void onClick(DialogInterface dialog, int which)
            {
            //-Do the save stuff here
            drawView.setDrawingCacheEnabled(true);
            String imgSaved = MediaStore.Images.Media.insertImage(
                    getContentResolver(), drawView.getDrawingCache(),
                    //- 1/12/16
                    //UUID.randomUUID().toString()+".png", "drawing");
                    "mypicture0192.png", "drawing");
            if(imgSaved!=null)
                {
                Toast savedToast = Toast.makeText(getApplicationContext(),
                        "Drawing saved to Gallery!", Toast.LENGTH_SHORT);
                savedToast.show();
                }
            else
                {
                Toast unsavedToast = Toast.makeText(getApplicationContext(),
                        "Oops! Image could not be saved.", Toast.LENGTH_SHORT);
                unsavedToast.show();
                }
            drawView.destroyDrawingCache();
            }
        });
        saveDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
        {
        public void onClick(DialogInterface dialog, int which)
            {
            dialog.cancel();
            }
        });
        saveDialog.show();
        }
    //############################################################################################
    //-DEBUG CODE
    //-SHOW THE TEXT FOR DEBUGGING
    public void DisplayTestText(String t)
        {
        testDisplay.setText(t);
        }
    //############################################################################################
    //- UNUSED CODE -//
    }
//################################################################################################