package com.atech.research.utils

import java.awt.Color
import java.awt.GraphicsEnvironment
import java.awt.Window
import javax.swing.JDialog
import javax.swing.JLabel
import javax.swing.SwingConstants
import javax.swing.border.EmptyBorder
import kotlin.concurrent.thread


actual class Toast {
    actual fun show(message: String, duration: ToastDuration) {
        val durationMs = when (duration) {
            ToastDuration.SHORT -> 2000L // 2 seconds
            ToastDuration.LONG -> 3500L  // 3.5 seconds
        }

        thread {
            val toast = JDialog()
            toast.apply {
                // Set window type first, before making it visible
                type = Window.Type.POPUP

                // Make the dialog undecorated (no title bar)
                isUndecorated = true

                // Create label with message
                val label = JLabel(message).apply {
                    horizontalAlignment = SwingConstants.CENTER
                    border = EmptyBorder(15, 25, 15, 25)
                    foreground = Color.WHITE
                }

                // Set dialog properties
                contentPane.apply {
                    add(label)
                    background = Color(0, 0, 0, 200) // Semi-transparent black
                }

                // Make the background slightly transparent
                opacity = 0.9f

                // Size and position the toast
                pack()
                setLocationRelativeTo(null)

                // Position at bottom of screen
                val ge = GraphicsEnvironment.getLocalGraphicsEnvironment()
                val screen = ge.maximumWindowBounds
                location.apply {
                    x = (screen.width - width) / 2
                    y = screen.height - height - 50 // 50 pixels from bottom
                }

                // Make sure it appears on top

                // Show the toast
                isVisible = true

                // Hide after duration
                thread {
                    Thread.sleep(durationMs)
                    dispose()
                }
            }
        }
    }
}