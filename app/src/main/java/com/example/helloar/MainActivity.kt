package com.example.helloar

import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.ar.core.Anchor
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.ux.ArFragment
import com.google.ar.sceneform.ux.BaseArFragment
import com.google.ar.sceneform.ux.TransformableNode
import java.util.function.Consumer


class MainActivity : AppCompatActivity() {

     var arFragment: ArFragment? = null
     var modelRenderable: ModelRenderable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        arFragment = supportFragmentManager.findFragmentById(R.id.ar_view) as ArFragment?
        setUpArModel()
        setUpPlane()
    }

    private fun setUpPlane() {
        arFragment?.setOnTapArPlaneListener(BaseArFragment.OnTapArPlaneListener { hitResult, plane, motionEvent ->
            var anchor: Anchor = hitResult.createAnchor()
            var anchorNode: AnchorNode = AnchorNode(anchor)
            anchorNode.setParent(arFragment!!.arSceneView.scene)
            createModel(anchorNode)
        })
    }

    private fun createModel(anchorNode: AnchorNode) {
        var node:TransformableNode = TransformableNode(arFragment?.transformationSystem)
        node.setParent(anchorNode)
        node.renderable = modelRenderable;
        node.select()
    }

    private fun setUpArModel(){
        ModelRenderable.builder().setSource(this,R.raw.frog).build().thenAccept(Consumer { renderable: ModelRenderable ->
                modelRenderable = renderable
            })
            .exceptionally {
                Toast.makeText(this@MainActivity, "Modeli Yüklerken Hata Çıktı", Toast.LENGTH_SHORT)
                    .show()
                null
            }
    }
}
