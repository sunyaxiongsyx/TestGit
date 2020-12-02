<?xml version="1.0" encoding="utf-8"?>
<flows xmlns="http://com/dc/esb/xmlbean/adaptor/flow">
	<flow name="UnpackIn"  location="in" flowType="adaptorFlow" description="拆组包接入流程">
		<service id="MonitorStep1BService" forcedExec="true" rule="" >
			<parameters>
			</parameters>
		</service>
		<service id="IPCheckerService" forcedExec="false" rule="" >
			<parameters>
			</parameters>
		</service>
		<service id="AccessCheckService" forcedExec="false" rule="" >
			<parameters>
			</parameters>
		</service>
		<service id="FlowControlService" forcedExec="false" rule="" >
			<parameters>
			</parameters>
		</service>
		<service id="UnpackService" forcedExec="false" rule="" >
			<parameters>
			</parameters>
		</service>
		<service id="ConsumerClientService" forcedExec="false" rule="" >
			<parameters>
			</parameters>
		</service>
		<service id="PackService" forcedExec="false" rule="" >
			<parameters>
			</parameters>
		</service>
		<service id="FlowRecycleService" forcedExec="true" rule="" >
			<parameters>
			</parameters>
		</service>
		<service id="MonitorStep4BService" forcedExec="true" rule="" >
			<parameters>
			</parameters>
		</service>
		<attributes>
		</attributes>
		<parameters>
		</parameters>
	</flow>
</flows>