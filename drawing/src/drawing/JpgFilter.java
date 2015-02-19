/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package drawing;

import java.io.File;
import java.io.FileFilter;

/**
 *
 * @author colbyg
 */
public class JpgFilter implements FileFilter{
    @Override
            public boolean accept(File pathname) {
                String name = pathname.getName();
                if (pathname.isDirectory()) {
                    return false;
                }
                if (name == null) {
                    return false;
                }
                return name.toLowerCase().endsWith(".jpg")
                        || name.toLowerCase().endsWith(".png")
                        || name.toLowerCase().endsWith(".gif");
            }
    
}
