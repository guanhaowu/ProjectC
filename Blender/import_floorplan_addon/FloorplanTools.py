import bpy
from bpy.types import Operator, Panel
from . FloorplanToolsFunctions import *

class FloorplanToolsPanel(Panel):
    bl_idname = "FLOORPLAN_PT_PANEL"
    bl_label = "Floorplan Panel"
    bl_category = "Floorplan"
    bl_space_type = "VIEW_3D"
    bl_region_type = "UI"

    def draw(self, context):
        layout = self.layout

        row = layout.row()
        row.operator('floorplan.align_floors', text="Align Floors")

        row = layout.row()
        row.operator('floorplan.hide_reference_images', text="Hide Refs")
        row.operator('floorplan.show_reference_images', text="Show Refs")

class AlignFloors(Operator):
    bl_idname = "floorplan.align_floors"
    bl_label = "Align Floors"
    bl_description = "Align two floors by selecting two room nodes that are in the same location on both floors."

    def execute(self, context):
        
        return alignFloors()

class HideReferenceImages(Operator):
    bl_idname = "floorplan.hide_reference_images"
    bl_label = "Hide Reference Images"
    bl_description = "Hide all floorplan reference images."

    def execute(self, context):
        return showReferenceImages(False)

class ShowReferenceImages(Operator):
    bl_idname = "floorplan.show_reference_images"
    bl_label = "Show Reference Images"
    bl_description = "Show all floorplan reference images."

    def execute(self, context):
        return showReferenceImages(True)