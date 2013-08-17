package br.ueg.portalLab.utils;

/* imports necess�rios */
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipInputStream;

/* fim imports necess�rios */

public class ProcessPPTHTMLGames {

	public static void main(String[] args) {
		String path = "C:\\Users\\Guiliano\\Desktop\\temp\\";
		String file = "Hymenoptera.zip";
		
		String fileSeparator = System.getProperty("file.separator");
		
		String dirInstall = file.replace(".zip", "");

		Zipper zipper = new Zipper();

		File filePath = new File(path.concat(fileSeparator).concat(file));
		File pathInstall = new File(path);

		String path2 = path.concat(fileSeparator).concat(dirInstall);
		
		String files;
		File folder = new File(path2);
		if(folder.exists() && folder.isDirectory()){
			try {
				Zipper.delete(folder);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		try {
			zipper.extrairZip(filePath, pathInstall);
		} catch (ZipException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// http://stackoverflow.com/questions/7532202/regular-expression-for-remove-html-links

		
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {

			if (listOfFiles[i].isFile()) {
				files = listOfFiles[i].getName();
				if (files.endsWith(".html") || files.endsWith(".HTML")
						|| files.endsWith(".htm") || files.endsWith(".HTM")) {
					System.out.println(files);

					String oldFileName = path2.concat(fileSeparator).concat(files);
					String tmpFileName = path2.concat(fileSeparator).concat("tmp".concat(files));

					BufferedReader br = null;
					BufferedWriter bw = null;
					try {
						br = new BufferedReader(new FileReader(oldFileName));
						bw = new BufferedWriter(new FileWriter(tmpFileName));
						String line;
						while ((line = br.readLine()) != null) {
							if (line.contains("<a")){
								line = line.replaceAll(
										"<a(\\s[^>]*)?>.*?(</a>)?", "");
								line = line.replace("Primeira página Voltar Continuar Última página Texto", "");
							}
							if (line.contains("</a>")){
								line = line.replace("</a>", "");
								line = line.replace("Primeira página Voltar Continuar Última página Texto", "");
							}
							bw.write(line + "\n");
						}
					} catch (Exception e) {
						e.printStackTrace();
						return;
					} finally {
						try {
							if (br != null)
								br.close();
						} catch (IOException e) {
							//
						}
						try {
							if (bw != null)
								bw.close();
						} catch (IOException e) {
							//
						}
					}
					// Once everything is complete, delete old file..
					File oldFile = new File(oldFileName);
					oldFile.delete();

					// And rename tmp file's name to old file name
					File newFile = new File(tmpFileName);
					newFile.renameTo(oldFile);

				}
			}
		}
	}
}
