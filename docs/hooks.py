import shutil
import os

def on_post_build(config):
    # Create .well-known directory in site
    well_known_src = os.path.join('docs', '.well-known')
    well_known_dest = os.path.join(config['site_dir'], '.well-known')
    
    # Create directory if it doesn't exist
    os.makedirs(well_known_dest, exist_ok=True)
    
    # Copy assetlinks.json
    src_file = os.path.join(well_known_src, 'assetlinks.json')
    dest_file = os.path.join(well_known_dest, 'assetlinks.json')
    shutil.copy2(src_file, dest_file)